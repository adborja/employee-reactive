package co.edu.cedesistemas.employees.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SysUserRole {
    @Id
    @JsonProperty("id")
    private UUID id;
    private String login;
    private String email;
    private String secret;
    private Boolean enabled;
    private List<String> roles;

    public static Mono<SysUserRole> fromRows(List<Map<String, Object>> rows) {
        return Mono.just(SysUserRole.builder()
                .id(UUID.fromString(rows.get(0).get("id").toString()))
                .login((String) rows.get(0).get("login"))
                .email((String) rows.get(0).get("email"))
                .secret((String) rows.get(0).get("secret"))
                .enabled((Boolean) rows.get(0).get("enabled"))
                .roles(rows.stream().map(f -> (String) f.get("role_name")).collect(Collectors.toList()))
                .build());
    }
}

