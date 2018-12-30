package com.xuanhatlu.hihi.repository;

import com.xuanhatlu.hihi.domain.Person;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("select p from Person p where (:name is null or p.name like :name)")
    List<Person> findAllByNameContains(@Param("name") String name);
}
