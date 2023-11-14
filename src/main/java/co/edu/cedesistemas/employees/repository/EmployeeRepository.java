package co.edu.cedesistemas.employees.repository;

import co.edu.cedesistemas.employees.model.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, UUID> {

}
