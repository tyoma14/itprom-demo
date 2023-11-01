package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.Profession;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionRepository extends BaseRepository<Profession>, RevisionRepository<Profession, Long, Long> {

}
