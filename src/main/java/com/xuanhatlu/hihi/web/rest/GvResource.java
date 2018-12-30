package com.xuanhatlu.hihi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xuanhatlu.hihi.service.GvService;
import com.xuanhatlu.hihi.web.rest.errors.BadRequestAlertException;
import com.xuanhatlu.hihi.web.rest.util.HeaderUtil;
import com.xuanhatlu.hihi.service.dto.GvDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Gv.
 */
@RestController
@RequestMapping("/api")
public class GvResource {

    private final Logger log = LoggerFactory.getLogger(GvResource.class);

    private static final String ENTITY_NAME = "gv";

    private final GvService gvService;

    public GvResource(GvService gvService) {
        this.gvService = gvService;
    }

    /**
     * POST  /gvs : Create a new gv.
     *
     * @param gvDTO the gvDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gvDTO, or with status 400 (Bad Request) if the gv has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gvs")
    @Timed
    public ResponseEntity<GvDTO> createGv(@RequestBody GvDTO gvDTO) throws URISyntaxException {
        log.debug("REST request to save Gv : {}", gvDTO);
        if (gvDTO.getId() != null) {
            throw new BadRequestAlertException("A new gv cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GvDTO result = gvService.save(gvDTO);
        return ResponseEntity.created(new URI("/api/gvs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gvs : Updates an existing gv.
     *
     * @param gvDTO the gvDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gvDTO,
     * or with status 400 (Bad Request) if the gvDTO is not valid,
     * or with status 500 (Internal Server Error) if the gvDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gvs")
    @Timed
    public ResponseEntity<GvDTO> updateGv(@RequestBody GvDTO gvDTO) throws URISyntaxException {
        log.debug("REST request to update Gv : {}", gvDTO);
        if (gvDTO.getId() == null) {
            return createGv(gvDTO);
        }
        GvDTO result = gvService.save(gvDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gvDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gvs : get all the gvs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gvs in body
     */
    @GetMapping("/gvs")
    @Timed
    public List<GvDTO> getAllGvs() {
        log.debug("REST request to get all Gvs");
        return gvService.findAll();
        }

    /**
     * GET  /gvs/:id : get the "id" gv.
     *
     * @param id the id of the gvDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gvDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gvs/{id}")
    @Timed
    public ResponseEntity<GvDTO> getGv(@PathVariable Long id) {
        log.debug("REST request to get Gv : {}", id);
        GvDTO gvDTO = gvService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gvDTO));
    }

    /**
     * DELETE  /gvs/:id : delete the "id" gv.
     *
     * @param id the id of the gvDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gvs/{id}")
    @Timed
    public ResponseEntity<Void> deleteGv(@PathVariable Long id) {
        log.debug("REST request to delete Gv : {}", id);
        gvService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
