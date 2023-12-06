package co.edu.cedesistemas.employees.service;

import co.edu.cedesistemas.employees.model.SysUserRole;
import lombok.RequiredArgsConstructor;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements ReactiveUserDetailsService {
    private final DatabaseClient databaseClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return findRolesByUsername(username)
                .flatMap(this::convert);
    }

    private Mono<SysUserRole> findRolesByUsername(String username) {
        var sql = "select distinct" +
                " su.id as id," +
                " su.login as login," +
                " su.email as email," +
                " su.secret as secret," +
                " su.enabled as enabled," +
                " sur.role_name as role_name" +
                " from sys_user su" +
                " inner join sys_user_role sur on su.login = sur.login" +
                " where su.login = :login";

        return databaseClient.sql(sql)
                .bind("login", username).fetch()
                .all()
                .bufferUntilChanged(result -> result.get("id"))
                .flatMap(SysUserRole::fromRows).next();
    }

    private static Function<SysUserRole, UserDetails> map() {
        return sur -> User.withUsername(sur.getLogin())
                .password(sur.getSecret())
                .disabled(!sur.getEnabled())
                .roles(sur.getRoles().toArray(String[]::new))
                .build();
    }

    private Mono<UserDetails> convert(SysUserRole sysUserRole) {
        return Mono.just(User.withUsername(sysUserRole.getLogin())
                .password(passwordEncoder.encode(sysUserRole.getSecret()))
                .disabled(!sysUserRole.getEnabled())
                .roles(sysUserRole.getRoles().toArray(String[]::new))
                .build());
    }
}
