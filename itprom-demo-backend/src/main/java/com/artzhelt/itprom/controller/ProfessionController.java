package com.artzhelt.itprom.controller;

import com.artzhelt.itprom.domain.Profession;
import com.artzhelt.itprom.repo.ProfessionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profession")
public class ProfessionController {

    private final ProfessionRepository professionRepository;

    public ProfessionController(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    @GetMapping
    public Iterable<Profession> findAll() {
        return professionRepository.findAll();
    }

    @PostMapping
    public Profession create(@RequestBody Profession profession) {
        return professionRepository.save(profession);
    }

    @GetMapping("/{id}")
    public Profession get(@PathVariable Long id) {
        return professionRepository.findById(id).get();
    }
    
    @PutMapping("/{id}")
    public Profession update(@RequestBody Profession newProfession, @PathVariable Long id) {
        return professionRepository.findById(id)
                .map(oldProfession -> {oldProfession.setName(newProfession.getName());
                                       oldProfession.setNote(newProfession.getNote());
                                       return professionRepository.save(oldProfession);}
                )
                .orElseGet(() -> {newProfession.setId(id);
                                  return professionRepository.save(newProfession);}
                );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        professionRepository.deleteById(id);
    }

}
