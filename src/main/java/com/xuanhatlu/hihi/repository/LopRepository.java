package com.xuanhatlu.hihi.repository;

import com.xuanhatlu.hihi.domain.Lop;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LopRepository extends JpaRepository<Lop, Long> {

}
