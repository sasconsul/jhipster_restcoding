package com.sasconsul.restcoding.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sasconsul.restcoding.domain.RegistryEntry;
import com.sasconsul.restcoding.service.RegistryService;
import com.sasconsul.restcoding.web.rest.util.HeaderUtil;
import com.sasconsul.restcoding.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Registry.
 */
@RestController
@RequestMapping("/api")
public class RegistryResource {

    private final Logger log = LoggerFactory.getLogger(RegistryResource.class);

    @Inject
    private RegistryService registryService;

    /**
     * POST  /registries : Create a new registry.
     *
     * @param registry the registry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registry, or with status 400 (Bad Request) if the registry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/registries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegistryEntry> createRegistry(@RequestBody RegistryEntry registry) throws URISyntaxException {
        log.debug("REST request to save Registry : {}", registry);
        if (registry.getId() != null || registry.getStringId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("registry", "idexists", "A new registry cannot already have an ID or stringId")).body(null);
        }
        RegistryEntry result = registryService.save(registry);
        return ResponseEntity.created(new URI("/api/registries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("registry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /registries : Updates an existing registry.
     *
     * @param registry the registry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registry,
     * or with status 400 (Bad Request) if the registry is not valid,
     * or with status 500 (Internal Server Error) if the registry couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/registries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegistryEntry> updateRegistry(@RequestBody RegistryEntry registry) throws URISyntaxException {
        log.debug("REST request to update Registry : {}", registry);
        if (registry.getId() == null) {
            return createRegistry(registry);
        }
        RegistryEntry result = registryService.save(registry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("registry", registry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /registries : get all the registries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of registries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/registries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RegistryEntry>> getAllRegistries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Registries");
        Page<RegistryEntry> page = registryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/registries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /registries/:id : get the "id" registry.
     *
     * @param id the id of the registry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registry, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/registries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegistryEntry> getRegistry(@PathVariable Long id) {
        log.debug("REST request to get Registry : {}", id);
        RegistryEntry registry = registryService.findOne(id);
        return Optional.ofNullable(registry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /registries/:id : delete the "id" registry.
     *
     * @param id the id of the registry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/registries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRegistry(@PathVariable Long id) {
        log.debug("REST request to delete Registry : {}", id);
        registryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("registry", id.toString())).build();
    }

}
