package com.xuanhatlu.hihi.repository.impl;

import com.xuanhatlu.hihi.domain.Student;
import com.xuanhatlu.hihi.repository.custom.StudentClassRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class StudentClassRepositoryCustomImpl implements StudentClassRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Student> findByVeryQuery(Integer name) {

        String x = "select id, name_student, age_student, class_id " +
            "from student where class_id LIKE ?;";
        Query query = entityManager.createNativeQuery(x, Student.class);
        query.setParameter(1, name);
        return query.getResultList();
    }
}
