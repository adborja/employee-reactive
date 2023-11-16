package co.edu.cedesistemas.employees.config;

import co.edu.cedesistemas.employees.model.Department;
import co.edu.cedesistemas.employees.model.Employee;
import co.edu.cedesistemas.employees.repository.DepartmentRepository;

import co.edu.cedesistemas.employees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
public class FunctionalConfig {
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;

    @Bean
    RouterFunction<ServerResponse> getAllDepartmentsRoute() {
        return route(GET("/departments"),
                req -> ok().body(departmentRepository.findAll(), Department.class));
    }

    @Bean
    RouterFunction<ServerResponse> getDepartmentByIdRoute() {
        return route(GET("/departments/{id}"),
                req -> ok().body(departmentRepository.findById(req.pathVariable("id")), Department.class));
    }

    @Bean
    RouterFunction<ServerResponse> deleteEmployee() {
        return route(DELETE("/employees/{id}"),
                req -> ok().body(employeeService.delete(UUID.fromString(req.pathVariable("id"))), Employee.class));
    }

    @Bean
    RouterFunction<ServerResponse> updateEmployee() {
        return route(PUT("/employees/{id}"),
                req -> req.body(BodyExtractors.toMono(Employee.class))
                        .flatMap(employee -> employeeService.update(req.pathVariable("id"), employee))
                        .flatMap(e -> ok().body(BodyInserters.fromValue(e)))
        );
    }

    /**
     * TODO: Create a router function to assign an employee to a department
     * */

    /**
     * TODO: Create a router function to assign the manager of a department
     * */


    /**
     * TODO: -
     * Create a resource (annotation) to manage the departments.
     * - Add an endpoint to retrieve all departments
     * - Add an endpoint to update the department name
     * - Add an endpoint to remove an employee from department
     * */
}
