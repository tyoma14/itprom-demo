package com.artzhelt.itprom.repo;

import com.artzhelt.itprom.domain.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
