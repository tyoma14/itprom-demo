package com.artzhelt.itprom.controller;

import com.artzhelt.itprom.domain.Profession;
import com.artzhelt.itprom.service.ProfessionService;
import com.artzhelt.itprom.spec.ProfessionByNameSpec;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profession")
public class ProfessionController extends CrudRestController<Profession> {

    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        super(professionService);
        this.professionService = professionService;
    }

    @PostMapping("/find-by-name")
    public Profession findByName(@RequestBody ProfessionByNameSpec spec) {
        return professionService.findOne(spec).orElse(null);
    }

    @PostMapping("/count-by-name")
    public Long countByName(@RequestBody ProfessionByNameSpec spec) {
        return professionService.count(spec);
    }

}
