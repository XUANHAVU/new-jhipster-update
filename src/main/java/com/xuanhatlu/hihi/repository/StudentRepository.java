package com.xuanhatlu.hihi.repository;

import com.xuanhatlu.hihi.domain.Student;
import com.xuanhatlu.hihi.repository.custom.StudentClassRepositoryCustom;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where (:abc is null or s.ageStudent like :abc)")
    List<Student> findAllByAgeStudentContains(@Param("abc") String name);

    @Query(value = "select * from student where (:xyz is null or class_id LIKE :xyz)", nativeQuery = true)
    List<Student> findStudentsByLopName_classContains(@Param("xyz") Integer name);

    @Query(value = "select * from student where (:abc is null or name_student like :abc)", nativeQuery = true)
    List<Student> findAllByNameStudentContainsNative(@Param("abc") String name);

}
