package com.xuanhatlu.hihi.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.xuanhatlu.hihi.service.StorageService;
import com.xuanhatlu.hihi.service.StudentService;
import com.xuanhatlu.hihi.service.dto.SearchStudentClassDTO;
import com.xuanhatlu.hihi.service.dto.SearchStudentDTO;
import com.xuanhatlu.hihi.web.rest.errors.BadRequestAlertException;
import com.xuanhatlu.hihi.web.rest.util.HeaderUtil;
import com.xuanhatlu.hihi.service.dto.StudentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing Student.
 */
@RestController
@RequestMapping("/api")
public class StudentResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);

    private static final String ENTITY_NAME = "student";

    private final StudentService studentService;

    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }


    List<String> files = new ArrayList<String>();

    /**
     * POST  /students : Create a new student.
     *
     * @param studentDTO the studentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentDTO, or with status 400 (Bad Request) if the student has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/students")
    @Timed
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) throws URISyntaxException {
        log.debug("REST request to save Student : {}", studentDTO);
        if (studentDTO.getId() != null) {
            throw new BadRequestAlertException("A new student cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentDTO result = studentService.save(studentDTO);
        return ResponseEntity.created(new URI("/api/students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /students : Updates an existing student.
     *
     * @param studentDTO the studentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentDTO,
     * or with status 400 (Bad Request) if the studentDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/students")
    @Timed
    public ResponseEntity<StudentDTO> updateStudent(@Valid @RequestBody StudentDTO studentDTO) throws URISyntaxException {
        log.debug("REST request to update Student : {}", studentDTO);
        if (studentDTO.getId() == null) {
            return createStudent(studentDTO);
        }
        StudentDTO result = studentService.save(studentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /students : get all the students.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of students in body
     */
    @GetMapping("/students")
    @Timed
    public List<StudentDTO> getAllStudents() {
        log.debug("REST request to get all Students");
        return studentService.findAll();
    }

    /**
     * GET  /students/:id : get the "id" student.
     *
     * @param id the id of the studentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/students/{id}")
    @Timed
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        log.debug("REST request to get Student : {}", id);
        StudentDTO studentDTO = studentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentDTO));
    }

    /**
     * DELETE  /students/:id : delete the "id" student.
     *
     * @param id the id of the studentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/students/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/students/search")
    @Timed
    public List<StudentDTO> searchStudentByName(@RequestBody SearchStudentDTO searchStudentDTO) throws URISyntaxException {
        log.debug("REST request to search Student : {}", searchStudentDTO);

        List<StudentDTO> lst = studentService.searchByName(searchStudentDTO.getName());

        return lst;
    }

    @PostMapping("/students/filter")
    @Timed
    public List<StudentDTO> searchStudentByClassStudent(@RequestBody SearchStudentClassDTO searchStudentClassDTO) throws URISyntaxException {
        log.debug("REST request to search Student in Class: {}", searchStudentClassDTO);

        List<StudentDTO> lst = studentService.searchByNameClass(searchStudentClassDTO.getName());

        return lst;
    }


    //    @Autowired
//    private ApplicationProperties applicationProperties;
//    @PostMapping("/students/upload")
//    @Timed
//    public String handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//            "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return "redirect:/upload";
//
//    }
    @PostMapping("/students/fileUpload")
    @Timed
    public ResponseEntity<String> saveUserDataAndFile(
        @RequestParam(value = "file") MultipartFile file) throws IOException {
        FileInputStream excelFile = null;
        try {
            String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            Random rand = new Random();
            int rand_int1 = rand.nextInt(1000);
            String s = String.valueOf(rand_int1);

            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName + "0" + s + "0" + file.getOriginalFilename()));

            String PathFile = rootLocation + "/" + fileName + "0" + s + "0" + file.getOriginalFilename();
            excelFile = new FileInputStream(new File(PathFile));

            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            Cell firstCell = firstRow.getCell(0);

            List<StudentDTO> list = new ArrayList<StudentDTO>();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();

                StudentDTO studentDTO = new StudentDTO();
                studentDTO.setNameStudent(currentRow.getCell(0).getStringCellValue());
//                studentDTO.setAgeStudent(currentRow.getCell(1).getStringCellValue());

                studentDTO.setAgeStudent(String.format(formatter.formatCellValue(currentRow.getCell(1))));
                studentDTO.setLopId(Long.parseLong(formatter.formatCellValue(currentRow.getCell(2))));
                studentService.save(studentDTO);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            excelFile.close();
        }
        return null;
    }

//    @PostMapping("/students/uploadfile")
//    @Timed
//    public String uploadFileMulti(@RequestParam("uploadfile") MultipartFile file) throws Exception {
//        try {
//            storageService.store(file);
//            files.add(file.getOriginalFilename());
//            return "You successfully uploaded - " + file.getOriginalFilename();
//        } catch (Exception e) {
//            throw new Exception("FAIL! Maybe You had uploaded the file before or the file's size > 500KB");
//        }
//    }
}
