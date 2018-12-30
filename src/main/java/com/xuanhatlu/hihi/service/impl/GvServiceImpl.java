package com.xuanhatlu.hihi.service.impl;

import com.xuanhatlu.hihi.service.GvService;
import com.xuanhatlu.hihi.domain.Gv;
import com.xuanhatlu.hihi.repository.GvRepository;
import com.xuanhatlu.hihi.service.dto.GvDTO;
import com.xuanhatlu.hihi.service.mapper.GvMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Gv.
 */
@Service
@Transactional
public class GvServiceImpl implements GvService {

    private final Logger log = LoggerFactory.getLogger(GvServiceImpl.class);

    private final GvRepository gvRepository;

    private final GvMapper gvMapper;

    public GvServiceImpl(GvRepository gvRepository, GvMapper gvMapper) {
        this.gvRepository = gvRepository;
        this.gvMapper = gvMapper;
    }

    /**
     * Save a gv.
     *
     * @param gvDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GvDTO save(GvDTO gvDTO) {
        log.debug("Request to save Gv : {}", gvDTO);
        Gv gv = gvMapper.toEntity(gvDTO);
        gv = gvRepository.save(gv);
        return gvMapper.toDto(gv);
    }

    /**
     * Get all the gvs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GvDTO> findAll() {
        log.debug("Request to get all Gvs");
        return gvRepository.findAll().stream()
            .map(gvMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one gv by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GvDTO findOne(Long id) {
        log.debug("Request to get Gv : {}", id);
        Gv gv = gvRepository.findOne(id);
        return gvMapper.toDto(gv);
    }

    /**
     * Delete the gv by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gv : {}", id);
        gvRepository.delete(id);
    }
}
