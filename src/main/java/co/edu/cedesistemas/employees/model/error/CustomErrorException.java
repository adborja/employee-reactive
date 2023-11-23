package co.edu.cedesistemas.employees.model.error;

import lombok.Getter;

public class CustomErrorException extends RuntimeException {
    @Getter
    private final ErrorResponse errorResponse;

    public CustomErrorException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
