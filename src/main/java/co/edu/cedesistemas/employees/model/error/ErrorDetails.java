package co.edu.cedesistemas.employees.model.error;

import co.edu.cedesistemas.employees.model.serde.ErrorDetailsSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonSerialize(using = ErrorDetailsSerializer.class)
@AllArgsConstructor
@Getter
public enum ErrorDetails {
    API_DEPARTMENT_NOT_FOUND(123, "department not found", "http://cedesistemas.edu.co/123"),
    API_EMPLOYEE_NOT_FOUND(124, "employee not found", "http://cedesistemas.edu.co/124"),
    API_CURRENCY_NOT_SUPPORTED(125, "currency not supported", "http://cedesistemas.edu.co/124");

    private final Integer errorCode;
    private final String errorMessage;
    private final String referenceUrl;
}
