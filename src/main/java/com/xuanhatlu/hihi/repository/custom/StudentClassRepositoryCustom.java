package com.xuanhatlu.hihi.repository.custom;

import com.xuanhatlu.hihi.domain.Student;

import java.util.List;

public interface StudentClassRepositoryCustom {
    List<Student> findByVeryQuery(Integer name);
}
