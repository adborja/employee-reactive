package co.edu.cedesistemas.employees.model.error;

import lombok.Getter;

import java.util.UUID;

public class EmployeeNotFoundException extends RuntimeException {
    @Getter
    private final UUID employeeId;
    private static final String MESSAGE = "employee not found";

    public EmployeeNotFoundException(UUID id) {
        super(MESSAGE);
        this.employeeId = id;
    }
}
