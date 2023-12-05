package co.edu.cedesistemas.employees.service;

import co.edu.cedesistemas.employees.model.Employee;
import co.edu.cedesistemas.employees.model.error.CustomErrorException;
import co.edu.cedesistemas.employees.model.error.EmployeeNotFoundException;
import co.edu.cedesistemas.employees.model.error.ErrorDetails;
import co.edu.cedesistemas.employees.model.error.ErrorResponse;
import co.edu.cedesistemas.employees.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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

    //TODO: Add preauthorize -> ADMIN
    public Mono<Employee> delete(UUID id) {
        return employeeRepository.findById(id)
                .flatMap(employee -> {
                    var emp = employeeRepository.deleteById(id).thenReturn(employee);
                    log.info("deleted employee: {} ({})", id, employee.getName());
                    return emp;
                }).switchIfEmpty(Mono.error(() -> {
                    var message = String.format("employee not found: %s", id);
                    var errorResponse = ErrorResponse.builder()
                            .traceId(RandomStringUtils.randomAlphanumeric(10))
                            .status(HttpStatus.NOT_FOUND)
                            .timestamp(OffsetDateTime.now())
                            .message(message)
                            .errors(List.of(ErrorDetails.API_EMPLOYEE_NOT_FOUND))
                            .build();
                    throw new CustomErrorException(message, errorResponse);
                }));
    }

    public Mono<Employee> save(Employee employee) {
        employee.setNewObj(true);
        employee.setId(UUID.randomUUID());
        return employeeRepository.save(employee);
    }

    //TODO: Add preauthorize -> ADMIN
    public Mono<Employee> update(String id, Employee employee) {
        return getById(UUID.fromString(id))
                .flatMap((existingEmployee) -> {
                    existingEmployee.setSalary(employee.getSalary());
                    existingEmployee.setName(employee.getName());
                    return employeeRepository.save(existingEmployee);
        });
    }
}
