package com.sasconsul.restcoding.web.rest;

import com.sasconsul.restcoding.JhipsterRestcodingApp;

import com.sasconsul.restcoding.domain.RegistryEntry;
import com.sasconsul.restcoding.repository.RegistryRepository;
import com.sasconsul.restcoding.service.RegistryService;
import com.sasconsul.restcoding.utils.RegistryUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RegistryResource REST controller.
 *
 * @see RegistryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterRestcodingApp.class)
public class RegistryResourceIntTest {

    private static final String DEFAULT_STRING = "AAAAA";
    private static final String UPDATED_STRING = "BBBBB";
    
    private static final Long DEFAULT_STRING_ID = RegistryUtils.validateGenerateStringId(DEFAULT_STRING);
    private static final Long UPDATED_STRING_ID = RegistryUtils.validateGenerateStringId(UPDATED_STRING);;

    @Inject
    private RegistryRepository registryRepository;

    @Inject
    private RegistryService registryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRegistryMockMvc;

    private RegistryEntry registryEntry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegistryResource registryResource = new RegistryResource();
        ReflectionTestUtils.setField(registryResource, "registryService", registryService);
        this.restRegistryMockMvc = MockMvcBuilders.standaloneSetup(registryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistryEntry createEntity(EntityManager em) {
        RegistryEntry registryEntry = new RegistryEntry()
                .string(DEFAULT_STRING);
        return registryEntry;
    }

    @Before
    public void initTest() {
        registryEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistry() throws Exception {
        int databaseSizeBeforeCreate = registryRepository.findAll().size();

        // Create the RegistryEntry

        byte[] registryEntryJsonBytes = TestUtil.convertObjectToJsonBytes(registryEntry);
		restRegistryMockMvc.perform(post("/api/registries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(registryEntryJsonBytes))
                .andExpect(status().isCreated());

        // Validate the Registry in the database
        List<RegistryEntry> registries = registryRepository.findAll();
        assertThat(registries).hasSize(databaseSizeBeforeCreate + 1);
        RegistryEntry testRegistryEntry = registries.get(registries.size() - 1);
        assertThat(testRegistryEntry.getStringId()).isEqualTo(DEFAULT_STRING_ID);
        assertThat(testRegistryEntry.getString()).isEqualTo(DEFAULT_STRING);
    }

    @Test
    @Transactional
    public void getAllRegistries() throws Exception {
        // Initialize the database
        registryRepository.saveAndFlush(registryEntry);

        // Get all the registryEntries
        restRegistryMockMvc.perform(get("/api/registries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(registryEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].stringId").value(hasItem(DEFAULT_STRING_ID.intValue())))
                .andExpect(jsonPath("$.[*].string").value(hasItem(DEFAULT_STRING.toString())));
    }

    @Test
    @Transactional
    public void getRegistry() throws Exception {
        // Initialize the database
        registryRepository.saveAndFlush(registryEntry);

        // Get the registry
        restRegistryMockMvc.perform(get("/api/registries/{id}", registryEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andDo(new ResultHandler() {
				
				@Override
				public void handle(MvcResult mvcResult) throws Exception {
					System.out.printf("API /api/registries/<id> returns: %s\n",mvcResult.getResponse().getContentAsString());
				}
			})
            .andExpect(jsonPath("$.id").value(registryEntry.getId().intValue()))
            .andExpect(jsonPath("$.stringId").value(DEFAULT_STRING_ID.intValue()))
            .andExpect(jsonPath("$.string").value(DEFAULT_STRING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegistry() throws Exception {
        // Get the registry
        restRegistryMockMvc.perform(get("/api/registries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistry() throws Exception {
        // Initialize the database
        registryService.save(registryEntry);

        int databaseSizeBeforeUpdate = registryRepository.findAll().size();

        // Update the registry
        RegistryEntry updatedRegistry = registryRepository.findOne(registryEntry.getId());
        updatedRegistry
                .stringId(UPDATED_STRING_ID)
                .string(UPDATED_STRING);

        restRegistryMockMvc.perform(put("/api/registries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRegistry)))
                .andExpect(status().isOk());

        // Validate the Registry in the database
        List<RegistryEntry> registries = registryRepository.findAll();
        assertThat(registries).hasSize(databaseSizeBeforeUpdate);
        RegistryEntry testRegistryEntry = registries.get(registries.size() - 1);
        assertThat(testRegistryEntry.getStringId()).isEqualTo(UPDATED_STRING_ID);
        assertThat(testRegistryEntry.getString()).isEqualTo(UPDATED_STRING);
    }

    @Test
    @Transactional
    public void deleteRegistry() throws Exception {
        // Initialize the database
        registryService.save(registryEntry);

        int databaseSizeBeforeDelete = registryRepository.findAll().size();

        // Get the registry
        restRegistryMockMvc.perform(delete("/api/registries/{id}", registryEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RegistryEntry> registries = registryRepository.findAll();
        assertThat(registries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
