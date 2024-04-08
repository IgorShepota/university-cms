package ua.foxminded.universitycms.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.foxminded.universitycms.repository.config.RepositoryConfig;

@Configuration
@ComponentScan(basePackages = {"ua.foxminded.universitycms.service",
    "ua.foxminded.universitycms.mapping"})
@Import(RepositoryConfig.class)
public class ServiceConfig {

}
