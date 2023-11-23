package co.edu.cedesistemas.employees.rest;

import co.edu.cedesistemas.employees.Application;
import co.edu.cedesistemas.employees.client.CurrencyClient;
import co.edu.cedesistemas.employees.model.Department;
import co.edu.cedesistemas.employees.model.DepartmentEmployee;
import co.edu.cedesistemas.employees.model.Employee;
import co.edu.cedesistemas.employees.model.error.EmployeeNotFoundException;
import co.edu.cedesistemas.employees.service.DepartmentService;
import co.edu.cedesistemas.employees.service.EmployeeService;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class EmployeeResourceIntegrationTest {
    private static final Faker FAKER = Faker.instance();

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private CurrencyClient currencyClient;

    @Test
    void given_employee_id_then_get_employee_by_id() {
        var employee = createDummyEmployee();

        given(employeeService.getById(employee.getId())).willReturn(Mono.just(employee));

        testClient.get()
                .uri(String.format("/employees/%s", employee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Employee.class).isEqualTo(employee);
    }

    @Test
    void get_all_employees() {
        var employees = createDummyEmployees(4);

        given(employeeService.findAll()).willReturn(Mono.just(employees));

        testClient.get()
                .uri("/employees")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Employee.class).isEqualTo(employees);
    }

    @Test
    void given_employee_id_and_department_id_then_assign_employee_to_department() {
        var department = createDummyDepartment();
        var employee = createDummyEmployee();

        var departmentEmployee = DepartmentEmployee.builder()
                .employeeId(employee.getId())
                .deptNumber(department.getDeptNumber())
                .fromDate(LocalDate.now())
                .toDate(LocalDate.MAX)
                .build();

        given(departmentService.assignEmployeeToDepartment(department.getDeptNumber(), employee.getId()))
                .willReturn(Mono.just(departmentEmployee));

        assert employee.getId() != null;

        testClient.post()
                .uri(String.format("/departments/%s/employees/%s", department.getDeptNumber(), employee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.employeeId").isEqualTo(employee.getId().toString())
                .jsonPath("$.deptNumber").isEqualTo(department.getDeptNumber());
    }

    @Test
    void given_employee_id_and_employee_info_then_update_employee() {
        var employee = createDummyEmployee();

        var newSalary = BigDecimal.valueOf(RandomUtils.nextDouble(60000, 99000));
        var employeeToUpdate = Employee.builder()
                .salary(newSalary)
                .name(employee.getName())
                .id(employee.getId())
                .build();

        assert employee.getId() != null;

        given(employeeService.getById(employee.getId())).willReturn(Mono.just(employee));
        given(employeeService.update(employee.getId().toString(), employeeToUpdate)).willReturn(Mono.just(employeeToUpdate));

        testClient.put()
                .uri(String.format("/employees/%s", employee.getId()))
                .body(Mono.just(employeeToUpdate), Employee.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.salary").isEqualTo(newSalary)
                .jsonPath("$.id").isEqualTo(employee.getId().toString());

        verify(employeeService, times(1))
                .update(employee.getId().toString(), employeeToUpdate);
    }

    @Test
    void given_employee_id_should_return_not_found() {
        var employee = createDummyEmployee();
        var currency = "cop";

        assert employee.getId() != null;

        given(currencyClient.getExchange(currency)).willReturn(4000d);
        given(employeeService.getById(employee.getId())).willReturn(Mono.error(() -> new EmployeeNotFoundException(employee.getId())));

        testClient.get()
                .uri(String.format("/v2/employees/%s?currency=%s", employee.getId(), currency))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.traceId").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.name());
    }

    @Test
    void given_not_supported_currency_should_return_bad_request() {
        var employee = createDummyEmployee();
        var currency = "eur";

        assert employee.getId() != null;

        given(currencyClient.getExchange(currency)).willReturn(1.1d);
        given(employeeService.getById(employee.getId())).willReturn(Mono.just(employee));

        testClient.get()
                .uri(String.format("/v2/employees/%s?currency=%s", employee.getId(), currency))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.traceId").isNotEmpty()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.type").isEqualTo("http://cedesistemas.edu.co/errors/invalid-currency");

        verify(currencyClient, times(0)).getExchange(anyString());
    }

    private static Department createDummyDepartment() {
        return Department.builder()
                .deptName(FAKER.commerce().department())
                .deptNumber(RandomStringUtils.random(4))
                .build();
    }

    private static Employee createDummyEmployee() {
        return Employee.builder()
                .id(UUID.randomUUID())
                .name(FAKER.name().fullName())
                .salary(BigDecimal.valueOf(RandomUtils.nextDouble(60000, 990000)))
                .build();
    }

    private static List<Employee> createDummyEmployees(int n) {
        return Stream
                .generate(EmployeeResourceIntegrationTest::createDummyEmployee)
                .limit(n)
                .collect(Collectors.toList());
    }
}
