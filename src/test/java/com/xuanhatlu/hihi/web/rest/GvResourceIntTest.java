package com.xuanhatlu.hihi.web.rest;

import com.xuanhatlu.hihi.XuanhaApp;

import com.xuanhatlu.hihi.domain.Gv;
import com.xuanhatlu.hihi.repository.GvRepository;
import com.xuanhatlu.hihi.service.GvService;
import com.xuanhatlu.hihi.service.dto.GvDTO;
import com.xuanhatlu.hihi.service.mapper.GvMapper;
import com.xuanhatlu.hihi.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.xuanhatlu.hihi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GvResource REST controller.
 *
 * @see GvResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XuanhaApp.class)
public class GvResourceIntTest {

    @Autowired
    private GvRepository gvRepository;

    @Autowired
    private GvMapper gvMapper;

    @Autowired
    private GvService gvService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGvMockMvc;

    private Gv gv;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GvResource gvResource = new GvResource(gvService);
        this.restGvMockMvc = MockMvcBuilders.standaloneSetup(gvResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gv createEntity(EntityManager em) {
        Gv gv = new Gv();
        return gv;
    }

    @Before
    public void initTest() {
        gv = createEntity(em);
    }

    @Test
    @Transactional
    public void createGv() throws Exception {
        int databaseSizeBeforeCreate = gvRepository.findAll().size();

        // Create the Gv
        GvDTO gvDTO = gvMapper.toDto(gv);
        restGvMockMvc.perform(post("/api/gvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gvDTO)))
            .andExpect(status().isCreated());

        // Validate the Gv in the database
        List<Gv> gvList = gvRepository.findAll();
        assertThat(gvList).hasSize(databaseSizeBeforeCreate + 1);
        Gv testGv = gvList.get(gvList.size() - 1);
    }

    @Test
    @Transactional
    public void createGvWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gvRepository.findAll().size();

        // Create the Gv with an existing ID
        gv.setId(1L);
        GvDTO gvDTO = gvMapper.toDto(gv);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGvMockMvc.perform(post("/api/gvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gvDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gv in the database
        List<Gv> gvList = gvRepository.findAll();
        assertThat(gvList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGvs() throws Exception {
        // Initialize the database
        gvRepository.saveAndFlush(gv);

        // Get all the gvList
        restGvMockMvc.perform(get("/api/gvs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gv.getId().intValue())));
    }

    @Test
    @Transactional
    public void getGv() throws Exception {
        // Initialize the database
        gvRepository.saveAndFlush(gv);

        // Get the gv
        restGvMockMvc.perform(get("/api/gvs/{id}", gv.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gv.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGv() throws Exception {
        // Get the gv
        restGvMockMvc.perform(get("/api/gvs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGv() throws Exception {
        // Initialize the database
        gvRepository.saveAndFlush(gv);
        int databaseSizeBeforeUpdate = gvRepository.findAll().size();

        // Update the gv
        Gv updatedGv = gvRepository.findOne(gv.getId());
        // Disconnect from session so that the updates on updatedGv are not directly saved in db
        em.detach(updatedGv);
        GvDTO gvDTO = gvMapper.toDto(updatedGv);

        restGvMockMvc.perform(put("/api/gvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gvDTO)))
            .andExpect(status().isOk());

        // Validate the Gv in the database
        List<Gv> gvList = gvRepository.findAll();
        assertThat(gvList).hasSize(databaseSizeBeforeUpdate);
        Gv testGv = gvList.get(gvList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGv() throws Exception {
        int databaseSizeBeforeUpdate = gvRepository.findAll().size();

        // Create the Gv
        GvDTO gvDTO = gvMapper.toDto(gv);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGvMockMvc.perform(put("/api/gvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gvDTO)))
            .andExpect(status().isCreated());

        // Validate the Gv in the database
        List<Gv> gvList = gvRepository.findAll();
        assertThat(gvList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGv() throws Exception {
        // Initialize the database
        gvRepository.saveAndFlush(gv);
        int databaseSizeBeforeDelete = gvRepository.findAll().size();

        // Get the gv
        restGvMockMvc.perform(delete("/api/gvs/{id}", gv.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gv> gvList = gvRepository.findAll();
        assertThat(gvList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gv.class);
        Gv gv1 = new Gv();
        gv1.setId(1L);
        Gv gv2 = new Gv();
        gv2.setId(gv1.getId());
        assertThat(gv1).isEqualTo(gv2);
        gv2.setId(2L);
        assertThat(gv1).isNotEqualTo(gv2);
        gv1.setId(null);
        assertThat(gv1).isNotEqualTo(gv2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GvDTO.class);
        GvDTO gvDTO1 = new GvDTO();
        gvDTO1.setId(1L);
        GvDTO gvDTO2 = new GvDTO();
        assertThat(gvDTO1).isNotEqualTo(gvDTO2);
        gvDTO2.setId(gvDTO1.getId());
        assertThat(gvDTO1).isEqualTo(gvDTO2);
        gvDTO2.setId(2L);
        assertThat(gvDTO1).isNotEqualTo(gvDTO2);
        gvDTO1.setId(null);
        assertThat(gvDTO1).isNotEqualTo(gvDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gvMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gvMapper.fromId(null)).isNull();
    }
}
