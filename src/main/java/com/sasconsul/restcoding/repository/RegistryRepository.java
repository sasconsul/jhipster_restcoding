package com.sasconsul.restcoding.repository;

import com.sasconsul.restcoding.domain.RegistryEntry;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Registry Entry entity.
 */
@SuppressWarnings("unused")
public interface RegistryRepository extends JpaRepository<RegistryEntry,Long> {

	List<RegistryEntry> findByStringId(Long stringId);

}
