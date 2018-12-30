package com.xuanhatlu.hihi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xuanhatlu.hihi.service.LopService;
import com.xuanhatlu.hihi.web.rest.errors.BadRequestAlertException;
import com.xuanhatlu.hihi.web.rest.util.HeaderUtil;
import com.xuanhatlu.hihi.service.dto.LopDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lop.
 */
@RestController
@RequestMapping("/api")
public class LopResource {

    private final Logger log = LoggerFactory.getLogger(LopResource.class);

    private static final String ENTITY_NAME = "lop";

    private final LopService lopService;

    public LopResource(LopService lopService) {
        this.lopService = lopService;
    }

    /**
     * POST  /lops : Create a new lop.
     *
     * @param lopDTO the lopDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lopDTO, or with status 400 (Bad Request) if the lop has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lops")
    @Timed
    public ResponseEntity<LopDTO> createLop(@Valid @RequestBody LopDTO lopDTO) throws URISyntaxException {
        log.debug("REST request to save Lop : {}", lopDTO);
        if (lopDTO.getId() != null) {
            throw new BadRequestAlertException("A new lop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LopDTO result = lopService.save(lopDTO);
        return ResponseEntity.created(new URI("/api/lops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lops : Updates an existing lop.
     *
     * @param lopDTO the lopDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lopDTO,
     * or with status 400 (Bad Request) if the lopDTO is not valid,
     * or with status 500 (Internal Server Error) if the lopDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lops")
    @Timed
    public ResponseEntity<LopDTO> updateLop(@Valid @RequestBody LopDTO lopDTO) throws URISyntaxException {
        log.debug("REST request to update Lop : {}", lopDTO);
        if (lopDTO.getId() == null) {
            return createLop(lopDTO);
        }
        LopDTO result = lopService.save(lopDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lopDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lops : get all the lops.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lops in body
     */
    @GetMapping("/lops")
    @Timed
    public List<LopDTO> getAllLops() {
        log.debug("REST request to get all Lops");
        return lopService.findAll();
        }

    /**
     * GET  /lops/:id : get the "id" lop.
     *
     * @param id the id of the lopDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lopDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lops/{id}")
    @Timed
    public ResponseEntity<LopDTO> getLop(@PathVariable Long id) {
        log.debug("REST request to get Lop : {}", id);
        LopDTO lopDTO = lopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lopDTO));
    }

    /**
     * DELETE  /lops/:id : delete the "id" lop.
     *
     * @param id the id of the lopDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lops/{id}")
    @Timed
    public ResponseEntity<Void> deleteLop(@PathVariable Long id) {
        log.debug("REST request to delete Lop : {}", id);
        lopService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
