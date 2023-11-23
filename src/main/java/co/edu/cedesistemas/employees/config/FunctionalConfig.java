package co.edu.cedesistemas.employees.config;

import co.edu.cedesistemas.employees.model.Department;
import co.edu.cedesistemas.employees.model.DepartmentEmployee;
import co.edu.cedesistemas.employees.model.Employee;

import co.edu.cedesistemas.employees.model.error.DepartmentNotFoundException;
import co.edu.cedesistemas.employees.service.DepartmentService;
import co.edu.cedesistemas.employees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
public class FunctionalConfig {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Bean
    RouterFunction<ServerResponse> getAllDepartmentsRoute() {
        return route(GET("/departments"),
                req -> ok().body(departmentService.getAllDepartments(), Department.class));
    }

    @Bean
    RouterFunction<ServerResponse> getDepartmentByIdRoute() {
        return route(GET("/departments/{id}"),
                req -> departmentService.getDepartmentById(req.pathVariable("id"))
                        .flatMap(department -> ok().body(Mono.just(department), Department.class))
                        .switchIfEmpty(ServerResponse.from(
                                ErrorResponse.builder(new DepartmentNotFoundException(req.pathVariable("id")), HttpStatus.NOT_FOUND, "department not found")
                                        .type(URI.create("http://cedesistemas.edu.co/errors/department-not-found"))
                                        .title("department not found")
                                        .build()))
        );
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
    @Bean
    RouterFunction<ServerResponse> assignEmployeeToDepartment() {
        return route(POST("/departments/{deptNumber}/employees/{employeeId}"),
                req -> ok().body(departmentService.assignEmployeeToDepartment(
                        req.pathVariable("deptNumber"), UUID.fromString(req.pathVariable("employeeId"))
                        ), DepartmentEmployee.class)
        );
    }

    /**
     * TODO: Create a router function to assign the manager of a department
     * */


    /**
     * TODO: -
     * Create a resource (annotation) to manage the departments:
     * - Add an endpoint to retrieve all departments
     * - Add an endpoint to update the department name
     * - Add an endpoint to remove an employee from department
     * */
}
