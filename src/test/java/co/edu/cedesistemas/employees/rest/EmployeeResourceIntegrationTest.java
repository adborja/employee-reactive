package co.edu.cedesistemas.employees.rest;

import co.edu.cedesistemas.employees.Application;
import co.edu.cedesistemas.employees.model.Department;
import co.edu.cedesistemas.employees.model.Employee;
import co.edu.cedesistemas.employees.service.DepartmentService;
import co.edu.cedesistemas.employees.service.EmployeeService;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
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
        //TODO: - get_all_employees
    }

    @Test
    void given_employee_id_and_department_id_then_assign_employee_to_department() {
        //TODO: - given_employee_id_and_department_id_then_assign_employee_to_department
    }

    @Test
    void given_employee_id_and_employee_info_then_update_employee() {
        //TODO: - given_employee_id_and_employee_info_then_update_employee
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
        var result = new ArrayList<Employee>();
        for (int i = 0; i < n; i ++) {
            result.add(Employee.builder()
                .id(UUID.randomUUID())
                .name(FAKER.name().fullName())
                .salary(BigDecimal.valueOf(RandomUtils.nextDouble(60000, 990000)))
                .build());
        }
        return result;
    }
}
