package co.edu.cedesistemas.employees.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("department_employee")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEmployee implements Persistable<UUID> {
    @Id
    @JsonProperty("id")
    private UUID id;

    @NotNull
    private UUID employeeId;

    @NotNull
    private String deptNumber;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @Override
    public UUID getId() {
        return id;
    }

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
