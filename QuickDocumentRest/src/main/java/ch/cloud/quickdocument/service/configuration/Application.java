package ch.cloud.quickdocument.service.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import ch.cloud.quickdocument.service.dam.configuration.QuickDocumentPersistentManager;
import ch.cloud.quickdocument.service.mvc.configuration.CustomWebMvcConfigurer;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
    scanBasePackages = {"ch.cloud.quickdocument.service.*"})

@ComponentScan(basePackages = {"ch.cloud.quickdocument.service.rest.v1.api", "ch.cloud.quickdocument.service.mvc.handler",
    "ch.cloud.quickdocument.service.mvc.facade.web"})

@Import({QuickDocumentPersistentManager.class, CustomWebMvcConfigurer.class})

@EnableScheduling

public class Application extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Application.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
