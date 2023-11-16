package co.edu.cedesistemas.employees.service;

import co.edu.cedesistemas.employees.model.Department;
import co.edu.cedesistemas.employees.model.DepartmentEmployee;
import co.edu.cedesistemas.employees.repository.DepartmentEmployeeRepository;
import co.edu.cedesistemas.employees.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentEmployeeRepository departmentEmployeeRepository;
    private final DepartmentRepository departmentRepository;

    public Flux<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Mono<Department> getDepartmentById(String id) {
        return departmentRepository.findById(id);
    }

    public Mono<DepartmentEmployee> assignEmployeeToDepartment(String deptNumber, UUID employeeId) {
        var departmentEmployee = DepartmentEmployee.builder()
                .newObj(true)
                .employeeId(employeeId)
                .deptNumber(deptNumber)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.MAX)
                .id(UUID.randomUUID())
                .build();
        return departmentEmployeeRepository.save(departmentEmployee);
    }
}
