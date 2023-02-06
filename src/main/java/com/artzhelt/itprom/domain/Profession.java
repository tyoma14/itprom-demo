package com.artzhelt.itprom.domain;

import jakarta.persistence.*;

@Entity
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profession_seq")
    @SequenceGenerator(name = "profession_seq", initialValue = 100)
    private Long id;

    private String name;

    private String note;

    public Profession() {
    }

    public Profession(String name, String note) {
        this.name = name;
        this.note = note;
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
}
