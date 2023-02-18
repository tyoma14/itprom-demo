package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
