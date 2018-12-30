package com.xuanhatlu.hihi.service.mapper;

import com.xuanhatlu.hihi.domain.*;
import com.xuanhatlu.hihi.service.dto.GvDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gv and its DTO GvDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GvMapper extends EntityMapper<GvDTO, Gv> {



    default Gv fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gv gv = new Gv();
        gv.setId(id);
        return gv;
    }
}
