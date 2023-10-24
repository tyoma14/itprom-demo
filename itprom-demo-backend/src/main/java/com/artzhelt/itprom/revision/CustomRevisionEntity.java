package com.artzhelt.itprom.revision;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

/**
 * Created by Artyom Zheltyshev on 24.10.2023
 * Сущность ревизии с доп. данными
 */
@RevisionEntity(CustomRevisionListener.class)
@Entity
@Table(name = "custom_rev_info")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomRevisionEntity extends DefaultRevisionEntity {

    private String username;

}
