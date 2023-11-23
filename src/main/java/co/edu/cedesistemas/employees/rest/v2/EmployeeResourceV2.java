package co.edu.cedesistemas.employees.rest.v2;

import co.edu.cedesistemas.employees.client.CurrencyClient;
import co.edu.cedesistemas.employees.model.Employee;
import co.edu.cedesistemas.employees.model.error.BadCurrencyException;
import co.edu.cedesistemas.employees.service.EmployeeService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v2/employees")
public class EmployeeResourceV2 {

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of("usd", "cop");
    private final EmployeeService employeeService;
    private final CurrencyClient currencyClient;

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<Employee>> getEmployeeById(@NotNull @PathVariable String id,
                                                          @RequestParam(name = "currency", required = false) String currency) {
        var _currency = currency != null ? currency : "usd";
        return employeeService.getById(UUID.fromString(id))
                .map(employee -> {
                    if (SUPPORTED_CURRENCIES.contains(_currency)) {
                        employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(currencyClient.getExchange(_currency))));
                    } else {
                        throw new BadCurrencyException(currency);
                    }
                    return employee;
                }).map(employee -> new ResponseEntity<>(employee, HttpStatus.OK));
    }

    //TODO: Add delete endpoint with CustomErrorException handling
    @DeleteMapping("{id}")
    public Mono<Employee> deleteEmployeeById(@NotNull @PathVariable String id) {
        return employeeService.delete(UUID.fromString(id));
    }
}
