package com.artzhelt.itprom.service;

import com.artzhelt.itprom.domain.BaseEntity;
import com.artzhelt.itprom.repo.BaseRepository;
import com.artzhelt.itprom.spec.BaseSpecification;

import java.util.List;
import java.util.Optional;

/**
 * Created by Artyom Zheltyshev on 01.11.2023
 * Базовый класс для сервисов с CRUD-операциями
 */
public class BaseCrudService<T extends BaseEntity> {

    private final BaseRepository<T> baseRepository;

    public BaseCrudService(BaseRepository<T> baseRepository) {
        this.baseRepository = baseRepository;
    }

    public List<T> findAll() {
        return findAll(new BaseSpecification<>());
    }

    public List<T> findAll(BaseSpecification<T> specification) {
        return baseRepository.findAll(specification);
    }

    public Optional<T> findOne(BaseSpecification<T> specification) {
        return baseRepository.findOne(specification);
    }

    public Optional<T> findOne(Long id) {
        return baseRepository.findById(id);
    }

    public Long count(BaseSpecification<T> specification) {
        return baseRepository.count(specification);
    }

    public T create(T entity) {
        return baseRepository.save(entity);
    }

    public T update(T newEntity, Long id) {
        return baseRepository.save(newEntity);
    }

    public void delete(Long id) {
        baseRepository.deleteById(id);
    }

}
