package co.edu.cedesistemas.employees.repository;

import co.edu.cedesistemas.employees.model.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DepartmentRepository extends ReactiveCrudRepository<Department, String>  {
}
