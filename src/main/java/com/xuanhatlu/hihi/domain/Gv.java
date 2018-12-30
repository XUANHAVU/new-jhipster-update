package com.xuanhatlu.hihi.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Gv.
 */
@Entity
@Table(name = "gv")
public class Gv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gv gv = (Gv) o;
        if (gv.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gv.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gv{" +
            "id=" + getId() +
            "}";
    }
}
