package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.Profession;
import org.springframework.data.repository.CrudRepository;

public interface ProfessionRepository extends CrudRepository<Profession, Long> {
}
