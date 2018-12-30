package com.xuanhatlu.hihi.service.mapper;

import com.xuanhatlu.hihi.domain.*;
import com.xuanhatlu.hihi.service.dto.LopDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lop and its DTO LopDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LopMapper extends EntityMapper<LopDTO, Lop> {


    @Mapping(target = "students", ignore = true)
    Lop toEntity(LopDTO lopDTO);

    default Lop fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lop lop = new Lop();
        lop.setId(id);
        return lop;
    }
}
