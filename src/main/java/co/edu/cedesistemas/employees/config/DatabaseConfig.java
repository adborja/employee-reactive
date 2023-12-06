package co.edu.cedesistemas.employees.config;

import io.r2dbc.spi.ConnectionFactories;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@EnableConfigurationProperties({R2dbcProperties.class, FlywayProperties.class})
public class DatabaseConfig {
    @Bean(initMethod = "migrate", name = "flyway")
    public Flyway flyway(FlywayProperties flywayProperties, R2dbcProperties r2dbcProperties) {
        return Flyway.configure()
                .dataSource(flywayProperties.getUrl(), r2dbcProperties.getUsername(), r2dbcProperties.getPassword())
                .locations(flywayProperties.getLocations().toArray(new String[0]))
                .baselineOnMigrate(true).load();
    }

    @Bean
    @DependsOn("flyway")
    public DatabaseClient rd2cDatabaseClient(@Value("${spring.r2dbc.url}") String url) {
        var connectionFactory = ConnectionFactories.get(url);
        return DatabaseClient.create(connectionFactory);
    }
}
