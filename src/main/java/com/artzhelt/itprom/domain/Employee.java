package com.artzhelt.itprom.domain;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", initialValue = 100)
    private Long id;

    private String fullName;

    @ManyToOne
    private Profession profession;

    @ManyToOne
    private Department department;

    private String note;

    public Employee() {
    }

    public Employee(String fullName, Profession profession, Department department, String note) {
        this.fullName = fullName;
        this.profession = profession;
        this.department = department;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
