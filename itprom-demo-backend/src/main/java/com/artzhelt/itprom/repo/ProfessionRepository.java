package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.Profession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface ProfessionRepository extends CrudRepository<Profession, Long>, RevisionRepository<Profession, Long, Long> {

}
