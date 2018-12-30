package com.xuanhatlu.hihi.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Lop entity.
 */
public class LopDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer name_class;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getName_class() {
        return name_class;
    }

    public void setName_class(Integer name_class) {
        this.name_class = name_class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LopDTO lopDTO = (LopDTO) o;
        if(lopDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lopDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LopDTO{" +
            "id=" + getId() +
            ", name_class=" + getName_class() +
            "}";
    }
}
