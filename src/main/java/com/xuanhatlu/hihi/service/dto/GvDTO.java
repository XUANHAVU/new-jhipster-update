package com.xuanhatlu.hihi.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Gv entity.
 */
public class GvDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GvDTO gvDTO = (GvDTO) o;
        if(gvDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gvDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GvDTO{" +
            "id=" + getId() +
            "}";
    }
}
