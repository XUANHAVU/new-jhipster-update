package com.xuanhatlu.hihi.web.rest;

import com.xuanhatlu.hihi.XuanhaApp;

import com.xuanhatlu.hihi.domain.Lop;
import com.xuanhatlu.hihi.repository.LopRepository;
import com.xuanhatlu.hihi.service.LopService;
import com.xuanhatlu.hihi.service.dto.LopDTO;
import com.xuanhatlu.hihi.service.mapper.LopMapper;
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
 * Test class for the LopResource REST controller.
 *
 * @see LopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XuanhaApp.class)
public class LopResourceIntTest {

    private static final String DEFAULT_NAME_CLASS = "1";
    private static final String UPDATED_NAME_CLASS = "2";

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private LopMapper lopMapper;

    @Autowired
    private LopService lopService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLopMockMvc;

    private Lop lop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LopResource lopResource = new LopResource(lopService);
        this.restLopMockMvc = MockMvcBuilders.standaloneSetup(lopResource)
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
    public static Lop createEntity(EntityManager em) {
        Lop lop = new Lop()
            .name_class(DEFAULT_NAME_CLASS);
        return lop;
    }

    @Before
    public void initTest() {
        lop = createEntity(em);
    }

    @Test
    @Transactional
    public void createLop() throws Exception {
        int databaseSizeBeforeCreate = lopRepository.findAll().size();

        // Create the Lop
        LopDTO lopDTO = lopMapper.toDto(lop);
        restLopMockMvc.perform(post("/api/lops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lopDTO)))
            .andExpect(status().isCreated());

        // Validate the Lop in the database
        List<Lop> lopList = lopRepository.findAll();
        assertThat(lopList).hasSize(databaseSizeBeforeCreate + 1);
        Lop testLop = lopList.get(lopList.size() - 1);
        assertThat(testLop.getName_class()).isEqualTo(DEFAULT_NAME_CLASS);
    }

    @Test
    @Transactional
    public void createLopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lopRepository.findAll().size();

        // Create the Lop with an existing ID
        lop.setId(1L);
        LopDTO lopDTO = lopMapper.toDto(lop);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLopMockMvc.perform(post("/api/lops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lop in the database
        List<Lop> lopList = lopRepository.findAll();
        assertThat(lopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkName_classIsRequired() throws Exception {
        int databaseSizeBeforeTest = lopRepository.findAll().size();
        // set the field null
        lop.setName_class(null);

        // Create the Lop, which fails.
        LopDTO lopDTO = lopMapper.toDto(lop);

        restLopMockMvc.perform(post("/api/lops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lopDTO)))
            .andExpect(status().isBadRequest());

        List<Lop> lopList = lopRepository.findAll();
        assertThat(lopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLops() throws Exception {
        // Initialize the database
        lopRepository.saveAndFlush(lop);

        // Get all the lopList
        restLopMockMvc.perform(get("/api/lops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name_class").value(hasItem(DEFAULT_NAME_CLASS)));
    }

    @Test
    @Transactional
    public void getLop() throws Exception {
        // Initialize the database
        lopRepository.saveAndFlush(lop);

        // Get the lop
        restLopMockMvc.perform(get("/api/lops/{id}", lop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lop.getId().intValue()))
            .andExpect(jsonPath("$.name_class").value(DEFAULT_NAME_CLASS));
    }

    @Test
    @Transactional
    public void getNonExistingLop() throws Exception {
        // Get the lop
        restLopMockMvc.perform(get("/api/lops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLop() throws Exception {
        // Initialize the database
        lopRepository.saveAndFlush(lop);
        int databaseSizeBeforeUpdate = lopRepository.findAll().size();

        // Update the lop
        Lop updatedLop = lopRepository.findOne(lop.getId());
        // Disconnect from session so that the updates on updatedLop are not directly saved in db
        em.detach(updatedLop);
        updatedLop
            .name_class(UPDATED_NAME_CLASS);
        LopDTO lopDTO = lopMapper.toDto(updatedLop);

        restLopMockMvc.perform(put("/api/lops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lopDTO)))
            .andExpect(status().isOk());

        // Validate the Lop in the database
        List<Lop> lopList = lopRepository.findAll();
        assertThat(lopList).hasSize(databaseSizeBeforeUpdate);
        Lop testLop = lopList.get(lopList.size() - 1);
        assertThat(testLop.getName_class()).isEqualTo(UPDATED_NAME_CLASS);
    }

    @Test
    @Transactional
    public void updateNonExistingLop() throws Exception {
        int databaseSizeBeforeUpdate = lopRepository.findAll().size();

        // Create the Lop
        LopDTO lopDTO = lopMapper.toDto(lop);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLopMockMvc.perform(put("/api/lops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lopDTO)))
            .andExpect(status().isCreated());

        // Validate the Lop in the database
        List<Lop> lopList = lopRepository.findAll();
        assertThat(lopList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLop() throws Exception {
        // Initialize the database
        lopRepository.saveAndFlush(lop);
        int databaseSizeBeforeDelete = lopRepository.findAll().size();

        // Get the lop
        restLopMockMvc.perform(delete("/api/lops/{id}", lop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lop> lopList = lopRepository.findAll();
        assertThat(lopList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lop.class);
        Lop lop1 = new Lop();
        lop1.setId(1L);
        Lop lop2 = new Lop();
        lop2.setId(lop1.getId());
        assertThat(lop1).isEqualTo(lop2);
        lop2.setId(2L);
        assertThat(lop1).isNotEqualTo(lop2);
        lop1.setId(null);
        assertThat(lop1).isNotEqualTo(lop2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LopDTO.class);
        LopDTO lopDTO1 = new LopDTO();
        lopDTO1.setId(1L);
        LopDTO lopDTO2 = new LopDTO();
        assertThat(lopDTO1).isNotEqualTo(lopDTO2);
        lopDTO2.setId(lopDTO1.getId());
        assertThat(lopDTO1).isEqualTo(lopDTO2);
        lopDTO2.setId(2L);
        assertThat(lopDTO1).isNotEqualTo(lopDTO2);
        lopDTO1.setId(null);
        assertThat(lopDTO1).isNotEqualTo(lopDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lopMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lopMapper.fromId(null)).isNull();
    }
}
