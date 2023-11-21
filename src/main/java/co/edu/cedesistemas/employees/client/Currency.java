package co.edu.cedesistemas.employees.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    private LocalDate date;
    private Map<String, Double> usd;
}
