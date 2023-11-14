package co.edu.cedesistemas.employees.rest;

import co.edu.cedesistemas.employees.model.Employee;
import co.edu.cedesistemas.employees.repository.EmployeeRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("employees")
public class EmployeeResource {
    private final EmployeeRepository repository;

    @PostMapping
    public Mono<ResponseEntity<Employee>> createEmployee(@RequestBody @Valid Mono<Employee> createEmployeeRequest) {
        return createEmployeeRequest.flatMap(repository::save).map(employee -> new ResponseEntity<>(employee, HttpStatus.CREATED));
    }

    @GetMapping
    public Mono<ResponseEntity<List<Employee>>> getEmployees() {
        return repository.findAll()
                .collectList()
                .map(employees -> new ResponseEntity<>(employees, HttpStatus.OK));
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<Employee>> getEmployeeById(@NotNull @PathVariable String id) {
        return repository.findById(UUID.fromString(id)).map(employee -> new ResponseEntity<>(employee, HttpStatus.OK));
    }
}
