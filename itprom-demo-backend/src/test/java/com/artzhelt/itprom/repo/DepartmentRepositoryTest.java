package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository repository;

    @Test
    void createDepartment() {
        Department department = new Department("Отдел Java разработки", "test");
        repository.save(department);
        Department foundDepartment = repository.findById(department.getId()).get();
        assertSame(department, foundDepartment);
    }

    @Test
    void createDepartmentHierarchy() {
        Department parent = new Department("Технологический отдел", "parent");
        repository.save(parent);
        Department child = new Department("Отдел Java разработки", "child", parent);
        repository.save(child);
        Department foundChild = repository.findById(child.getId()).get();
        assertSame(child, foundChild);
        assertSame(parent, foundChild.getParent());
    }
}