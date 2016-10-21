package com.sasconsul.restcoding.service;

import com.sasconsul.restcoding.domain.RegistryEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Registry.
 */
public interface RegistryService {

    /**
     * Save a registry.
     *
     * @param registry the entity to save
     * @return the persisted entity
     */
    RegistryEntry save(RegistryEntry registry);

    /**
     *  Get all the registries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegistryEntry> findAll(Pageable pageable);

    /**
     *  Get the "id" registry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegistryEntry findOne(Long id);
    
    /**
     * Get the string "id" of the registry entry
     * 
     * @param stringId the StringId of the entity
     * @return the entity
     */
    List<RegistryEntry> findByStringId(Long stringId);

    /**
     *  Delete the "id" registry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
