package co.edu.cedesistemas.employees.model.error;

import lombok.Getter;

public class DepartmentNotFoundException extends RuntimeException {
    @Getter
    private final String deptNumber;
    private static final String MESSAGE = "department not found";

    public DepartmentNotFoundException(String deptNumber) {
        super(MESSAGE);
        this.deptNumber = deptNumber;
    }
}
