package com.xuanhatlu.hihi.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Student entity.
 */
public class StudentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 45)
    private String nameStudent;

    @NotNull
    @Size(max = 45)
    private String ageStudent;

    @NotNull
    @Size(max = 45)
    private String address;

    @NotNull
    @Size(max = 45)
    private String phone;

    @NotNull
    @Size(max = 45)
    private String email;

    private Long lopId;

    private String lopName;

    public Long getLopId() {
        return lopId;
    }

    public void setLopId(Long lopId) {
        this.lopId = lopId;
    }

    public String getLopName() {
        return lopName;
    }

    public void setLopName(String lopName) {
        this.lopName = lopName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getAgeStudent() {
        return ageStudent;
    }

    public void setAgeStudent(String ageStudent) {
        this.ageStudent = ageStudent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentDTO studentDTO = (StudentDTO) o;
        if(studentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + id +
            ", nameStudent='" + nameStudent + '\'' +
            ", ageStudent='" + ageStudent + '\'' +
            ", address='" + address + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            ", lopId=" + lopId +
            ", lopName=" + lopName +
            '}';
    }
}
