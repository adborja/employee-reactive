package co.edu.cedesistemas.employees.service;

import co.edu.cedesistemas.employees.model.Employee;
import co.edu.cedesistemas.employees.model.error.EmployeeNotFoundException;
import co.edu.cedesistemas.employees.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Mono<List<Employee>> findAll() {
        return employeeRepository.findAll()
                .collectList();
    }

    public Mono<Employee> getById(UUID id) {
        return employeeRepository.findById(id)
               .switchIfEmpty(Mono.error(() -> new EmployeeNotFoundException(id)));
    }

    public Mono<Employee> delete(UUID id) {
        return employeeRepository.findById(id)
                .flatMap(employee -> employeeRepository.deleteById(id).thenReturn(employee))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Employee> save(Employee employee) {
        employee.setNewObj(true);
        employee.setId(UUID.randomUUID());
        return employeeRepository.save(employee);
    }

    public Mono<Employee> update(String id, Employee employee) {
        return getById(UUID.fromString(id))
                .flatMap((existingEmployee) -> {
                    existingEmployee.setSalary(employee.getSalary());
                    existingEmployee.setName(employee.getName());
                    return employeeRepository.save(existingEmployee);
        });
    }
}
