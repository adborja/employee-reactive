package co.edu.cedesistemas.employees.repository;

import co.edu.cedesistemas.employees.model.DepartmentEmployee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface DepartmentEmployeeRepository extends ReactiveCrudRepository<DepartmentEmployee, UUID> {
}
