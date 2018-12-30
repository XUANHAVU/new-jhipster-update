package com.xuanhatlu.hihi.service;

import com.xuanhatlu.hihi.service.dto.LopDTO;
import java.util.List;

/**
 * Service Interface for managing Lop.
 */
public interface LopService {

    /**
     * Save a lop.
     *
     * @param lopDTO the entity to save
     * @return the persisted entity
     */
    LopDTO save(LopDTO lopDTO);

    /**
     * Get all the lops.
     *
     * @return the list of entities
     */
    List<LopDTO> findAll();

    /**
     * Get the "id" lop.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LopDTO findOne(Long id);

    /**
     * Delete the "id" lop.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
