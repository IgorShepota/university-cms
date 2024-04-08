package ua.foxminded.universitycms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;
import ua.foxminded.universitycms.service.config.ServiceConfig;

@SpringBootApplication(exclude = {
    HibernateJpaAutoConfiguration.class
})
@Import(ServiceConfig.class)
public class UniversityCmsApplication {

  public static void main(String[] args) {

    SpringApplication.run(UniversityCmsApplication.class, args);

  }

}
