package co.edu.cedesistemas.employees.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("department")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Persistable<String> {
    @Id
    @JsonProperty("number")
    private String deptNumber;

    @JsonProperty("name")
    private String deptName;

    @JsonIgnore
    @Override
    public String getId() {
        return deptNumber;
    }

    @Transient
    @JsonIgnore
    private boolean newDepartment;

    @Override
    @JsonIgnore
    @Transient
    public boolean isNew() {
        return this.newDepartment || deptNumber == null;
    }
}
