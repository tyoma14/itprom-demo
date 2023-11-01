package com.artzhelt.itprom.controller;

import com.artzhelt.itprom.domain.BaseEntity;
import com.artzhelt.itprom.service.BaseCrudService;
import com.artzhelt.itprom.spec.BaseSpecification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Artyom Zheltyshev on 01.11.2023
 * Базовый REST-контроллер для CRUD-операций
 */
public abstract class CrudRestController<T extends BaseEntity> {

    private final BaseCrudService<T> baseCrudService;

    protected CrudRestController(BaseCrudService<T> baseCrudService) {
        this.baseCrudService = baseCrudService;
    }

    @GetMapping
    public Iterable<T> findAll() {
        return baseCrudService.findAll();
    }

    @PostMapping("/find-all")
    public List<T> findAll(@RequestBody BaseSpecification<T> specification) {
        return baseCrudService.findAll(specification);
    }

    @PostMapping("/count")
    public Long count(@RequestBody BaseSpecification<T> specification) {
        return baseCrudService.count(specification);
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return baseCrudService.create(entity);
    }

    @GetMapping("/{id}")
    public T get(@PathVariable Long id) {
        return baseCrudService.findOne(id).orElse(null);
    }

    @PutMapping("/{id}")
    public T update(@RequestBody T newEntity, @PathVariable Long id) {
        return baseCrudService.update(newEntity, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        baseCrudService.delete(id);
    }

}
