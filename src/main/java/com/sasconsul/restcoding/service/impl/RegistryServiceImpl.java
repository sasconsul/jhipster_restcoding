package com.sasconsul.restcoding.service.impl;

import com.sasconsul.restcoding.service.RegistryService;
import com.sasconsul.restcoding.domain.RegistryEntry;
import com.sasconsul.restcoding.repository.RegistryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;

/**
 * Service Implementation for managing Registry.
 */
@Service
@Transactional
public class RegistryServiceImpl implements RegistryService{

    private final Logger log = LoggerFactory.getLogger(RegistryServiceImpl.class);

    @Inject
    private RegistryRepository registryRepository;

    /**
     * Save a registry.
     *
     * @param registry the entity to save
     * @return the persisted entity
     */
    public RegistryEntry save(RegistryEntry registry) {
        log.debug("Request to save RegistryEntry : {}", registry);

        // generate the stringId
        //
        registry.setStringId(generateStringId(registry.getString(),0 ) );
        RegistryEntry result = registryRepository.save(registry);
        return result;
    }

    /**
     * Generate a stringId from the input string
     *
     * @param s the string for the registry entry
     * @param i index into the string. Use 0 for the whole string
     * @return
     */
    public long  generateStringId(String s, int i) {

        if (s == null || i >= s.length() ) {
            return 0;
        }
        if (i == 0) {
            return generateStringId(s, i+1) + s.codePointAt(i);
        } else {
            if (s.codePointAt(i-1) == s.codePointAt(i)) {
                return generateStringId(s, i+1) + s.codePointAt(i);
            } else {
                return generateStringId(s, i+1) + s.codePointAt(i) + s.codePointAt(i-1);
            }
        }
    }

    /**
     *  Get all the registry Entries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RegistryEntry> findAll(Pageable pageable) {
        log.debug("Request to get all Registries");
        Page<RegistryEntry> result = registryRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one registry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RegistryEntry findOne(Long id) {
        log.debug("Request to get RegistryEntry : {}", id);
        RegistryEntry registry = registryRepository.findOne(id);
        return registry;
    }

    /**
     *  Delete the  registry by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RegistryEntry : {}", id);
        registryRepository.delete(id);
    }

    /**
     *  Get one registry entry by String id.
     *
     *  @param id the String id of the entity
     *  @return the entity
     */
	@Override
	public List<RegistryEntry> findByStringId(Long stringId) {
        log.debug("Request to get RegistryEntry  by String : {}", stringId);
        List<RegistryEntry> result = registryRepository.findByStringId(stringId);
        return result;
    }
}
