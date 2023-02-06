package com.artzhelt.itprom.controller;

import com.artzhelt.itprom.domain.Department;
import com.artzhelt.itprom.domain.Profession;
import com.artzhelt.itprom.repo.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @PostMapping
    public Department create(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @GetMapping("/{id}")
    public Department get(@PathVariable Long id) {
        return departmentRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public Department update(@RequestBody Department newDepartment, @PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(oldDepartment -> {
                    oldDepartment.setName(newDepartment.getName());
                    oldDepartment.setNote(newDepartment.getNote());
                    oldDepartment.setParent(newDepartment.getParent());
                    return departmentRepository.save(oldDepartment);}
                )
                .orElseGet(() -> {newDepartment.setId(id);
                    return departmentRepository.save(newDepartment);}
                );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        departmentRepository.deleteById(id);
    }
}
