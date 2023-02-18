package com.artzhelt.itprom.controller;

import com.artzhelt.itprom.domain.Employee;
import com.artzhelt.itprom.repo.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @PostMapping
    public Employee create(@RequestBody Employee department) {
        return employeeRepository.save(department);
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable Long id) {
        return employeeRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public Employee update(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(oldEmployee -> {
                    oldEmployee.setFullName(newEmployee.getFullName());
                    oldEmployee.setProfession(newEmployee.getProfession());
                    oldEmployee.setDepartment(newEmployee.getDepartment());
                    oldEmployee.setNote(newEmployee.getNote());
                    return employeeRepository.save(oldEmployee);}
                )
                .orElseGet(() -> {newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);}
                );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }
}
