package com.xuanhatlu.hihi.service.mapper;

import com.xuanhatlu.hihi.domain.*;
import com.xuanhatlu.hihi.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", uses = {LopMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "lop.id", target = "lopId")
    @Mapping(source = "lop.name_class", target = "lopName")
    StudentDTO toDto(Student student);

    @Mapping(source = "lopId", target = "lop")
    Student toEntity(StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
