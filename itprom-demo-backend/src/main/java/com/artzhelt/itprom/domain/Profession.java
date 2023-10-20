package com.artzhelt.itprom.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

@Entity
@Audited
@ToString
@EqualsAndHashCode
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profession_seq")
    @SequenceGenerator(name = "profession_seq", initialValue = 100)
    private Long id;

    private String name;

    private String note;

    public Profession() {
    }

    public Profession(Long id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
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
