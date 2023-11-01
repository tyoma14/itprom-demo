package com.artzhelt.itprom.service;

import com.artzhelt.itprom.domain.Profession;
import com.artzhelt.itprom.repo.ProfessionRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Artyom Zheltyshev on 27.10.2023
 */
@Service
public class ProfessionService extends BaseCrudService<Profession> {

    private final ProfessionRepository professionRepository;

    public ProfessionService(ProfessionRepository professionRepository) {
        super(professionRepository);
        this.professionRepository = professionRepository;
    }

}
