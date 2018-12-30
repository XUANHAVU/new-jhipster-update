package com.xuanhatlu.hihi.service;

import com.xuanhatlu.hihi.service.dto.GvDTO;
import java.util.List;

/**
 * Service Interface for managing Gv.
 */
public interface GvService {

    /**
     * Save a gv.
     *
     * @param gvDTO the entity to save
     * @return the persisted entity
     */
    GvDTO save(GvDTO gvDTO);

    /**
     * Get all the gvs.
     *
     * @return the list of entities
     */
    List<GvDTO> findAll();

    /**
     * Get the "id" gv.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GvDTO findOne(Long id);

    /**
     * Delete the "id" gv.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
