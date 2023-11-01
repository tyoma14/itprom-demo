package com.artzhelt.itprom.domain;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.envers.Audited;

@Entity
@Audited
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profession extends BaseEntity {

    private String name;
    private String note;

}
