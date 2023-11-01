package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.Profession;
import com.artzhelt.itprom.spec.ProfessionByNameSpec;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@ActiveProfiles("pgsql")
class ProfessionRepositoryTest {

    @Autowired
    private ProfessionRepository repository;

    @Test
    @Disabled
    void createProfession() {
        Profession profession = new Profession("java developer", "test");
        repository.save(profession);
        Profession foundProfession = repository.findById(profession.getId()).get();
        assertSame(profession, foundProfession);
    }

    @Test
    void count() {
        long count = repository.count(new ProfessionByNameSpec(1L, "Разработчик"));
        assertEquals(1, count);
    }

}