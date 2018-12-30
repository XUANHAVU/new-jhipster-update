package com.xuanhatlu.hihi.repository;

import com.xuanhatlu.hihi.domain.Gv;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Gv entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GvRepository extends JpaRepository<Gv, Long> {

}
