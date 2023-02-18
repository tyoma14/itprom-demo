package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.Profession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProfessionRepositoryTest {

    @Autowired
    private ProfessionRepository repository;

    @Test
    void createProfession() {
        Profession profession = new Profession("java developer", "test");
        repository.save(profession);
        Profession foundProfession = repository.findById(profession.getId()).get();
        assertSame(profession, foundProfession);
    }
}