package com.xuanhatlu.hihi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lop.
 */
@Entity
@Table(name = "lop")
public class Lop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_class", nullable = false)
    private Integer name_class;

    @OneToMany(mappedBy = "lop")
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getName_class() {
        return name_class;
    }

    public Lop name_class(Integer name_class) {
        this.name_class = name_class;
        return this;
    }

    public void setName_class(Integer name_class) {
        this.name_class = name_class;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Lop students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Lop addStudent(Student student) {
        this.students.add(student);
        student.setLop(this);
        return this;
    }

    public Lop removeStudent(Student student) {
        this.students.remove(student);
        student.setLop(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
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
        Lop lop = (Lop) o;
        if (lop.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lop.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lop{" +
            "id=" + getId() +
            ", name_class=" + getName_class() +
            "}";
    }
}
