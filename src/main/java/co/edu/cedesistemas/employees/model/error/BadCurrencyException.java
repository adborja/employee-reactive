package co.edu.cedesistemas.employees.model.error;

import lombok.Getter;

public class BadCurrencyException extends RuntimeException {
    @Getter
    private final String currency;
    private static final String MESSAGE = "invalid currency: %s";

    public BadCurrencyException(String currency) {
        super(String.format(MESSAGE, currency));
        this.currency = currency;
    }
}
