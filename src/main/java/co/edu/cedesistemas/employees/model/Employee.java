package co.edu.cedesistemas.employees.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("employee")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Employee implements Persistable<UUID> {
    @Id
    @JsonProperty("id")
    private UUID id;

    @NotNull
    @Size(max = 100, message = "The property 'name' must be less than or equal to 100 characters.")
    private String name;

    @NotNull
    private BigDecimal salary;

    @Transient
    @JsonIgnore
    private boolean newObj;

    @Override
    @JsonIgnore
    @Transient
    public boolean isNew() {
        return this.newObj || id == null;
    }
}
