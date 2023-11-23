package co.edu.cedesistemas.employees.model.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String traceId;
    private OffsetDateTime timestamp;
    private HttpStatus status;
    private String message;
    private List<ErrorDetails> errors;
}
