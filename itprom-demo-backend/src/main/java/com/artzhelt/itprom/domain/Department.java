package com.artzhelt.itprom.domain;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    @SequenceGenerator(name = "department_seq", initialValue = 100)
    private Long id;

    private String name;

    private String note;

    @ManyToOne
    private Department parent;


    public Department() {
    }

    public Department(String name, String note, Department parent) {
        this.name = name;
        this.note = note;
        this.parent = parent;
    }

    public Department(String name, String note) {
        this(name, note, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }
}
