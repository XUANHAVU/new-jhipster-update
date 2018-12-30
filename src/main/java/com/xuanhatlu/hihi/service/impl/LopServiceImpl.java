package com.xuanhatlu.hihi.service.impl;

import com.xuanhatlu.hihi.service.LopService;
import com.xuanhatlu.hihi.domain.Lop;
import com.xuanhatlu.hihi.repository.LopRepository;
import com.xuanhatlu.hihi.service.dto.LopDTO;
import com.xuanhatlu.hihi.service.mapper.LopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Lop.
 */
@Service
@Transactional
public class LopServiceImpl implements LopService {

    private final Logger log = LoggerFactory.getLogger(LopServiceImpl.class);

    private final LopRepository lopRepository;

    private final LopMapper lopMapper;

    public LopServiceImpl(LopRepository lopRepository, LopMapper lopMapper) {
        this.lopRepository = lopRepository;
        this.lopMapper = lopMapper;
    }

    /**
     * Save a lop.
     *
     * @param lopDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LopDTO save(LopDTO lopDTO) {
        log.debug("Request to save Lop : {}", lopDTO);
        Lop lop = lopMapper.toEntity(lopDTO);
        lop = lopRepository.save(lop);
        return lopMapper.toDto(lop);
    }

    /**
     * Get all the lops.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LopDTO> findAll() {
        log.debug("Request to get all Lops");
        return lopRepository.findAll().stream()
            .map(lopMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one lop by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LopDTO findOne(Long id) {
        log.debug("Request to get Lop : {}", id);
        Lop lop = lopRepository.findOne(id);
        return lopMapper.toDto(lop);
    }

    /**
     * Delete the lop by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lop : {}", id);
        lopRepository.delete(id);
    }
}
