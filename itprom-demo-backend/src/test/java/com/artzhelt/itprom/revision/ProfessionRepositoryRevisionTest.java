package com.artzhelt.itprom.revision;

import com.artzhelt.itprom.domain.Profession;
import com.artzhelt.itprom.repo.ProfessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Artyom Zheltyshev on 20.10.2023
 */
@SpringBootTest
@Slf4j
class ProfessionRepositoryRevisionTest {

    @Autowired
    ProfessionRepository professionRepository;

    @Test
    void checkRevision() {
        Profession profession = prepareProfessionHistory();
        Revisions<Long, Profession> revisions = professionRepository.findRevisions(profession.getId());
        Iterator<Revision<Long, Profession>> iterator = revisions.iterator();

        Revision<Long, Profession> firstRevision = iterator.next();
        assertEquals(RevisionMetadata.RevisionType.INSERT, firstRevision.getMetadata().getRevisionType());
        assertEquals(new Profession(profession.getId(), "Разработчик", "-"), firstRevision.getEntity());

        Revision<Long, Profession> secondRevision = iterator.next();
        assertEquals(RevisionMetadata.RevisionType.UPDATE, secondRevision.getMetadata().getRevisionType());
        assertEquals(new Profession(profession.getId(), "Developer", "-"), secondRevision.getEntity());

        Revision<Long, Profession> thirdRevision = iterator.next();
        assertEquals(RevisionMetadata.RevisionType.DELETE, thirdRevision.getMetadata().getRevisionType());
    }

    private Profession prepareProfessionHistory() {
        Profession profession = new Profession("Разработчик", "-");
        Profession created = professionRepository.save(profession);

        created.setName("Developer");
        Profession updated = professionRepository.save(created);

        professionRepository.delete(updated);
        return updated;
    }

}