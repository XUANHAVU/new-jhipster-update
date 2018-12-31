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
    private String name_class;

    @NotNull
    private String soluong;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_class() {
        return name_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
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
            "id=" + id +
            ", name_class='" + name_class + '\'' +
            ", soluong='" + soluong + '\'' +
            '}';
    }
}
