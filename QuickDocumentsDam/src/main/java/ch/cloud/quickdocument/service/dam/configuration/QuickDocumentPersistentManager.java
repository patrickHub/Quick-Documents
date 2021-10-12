package ch.cloud.quickdocument.service.dam.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.sql.DataSource;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ch.cloud.quickdocument.service.dam.utilities.Utility;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
@Configuration
public class QuickDocumentPersistentManager {

  private static final Logger LOG = Logger.getLogger(QuickDocumentPersistentManager.class);

  private final String PROJECT_NAME =
      Utility.isLocalDeployment() ? "QuickDocumentDatabaseDeveloper.cfg.properties" : "QuickDocumentDatabaseProduction.cfg.properties";

  private final String DATABASE_IP = Utility.isLocalDeployment() ? "127.0.0.1" : System.getenv("QUICKDOCUMENT_DATABASE_SERVER");

  @Autowired
  ResourceLoader resourceLoader;

  @Bean
  public DataSource dataSource() {

    System.out.println("FOUND QUICKDOCUMENT_DATABASE_SERVER: " + DATABASE_IP);

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    Properties dbConfigProperties = loadDbConfigProperties();
    dataSource.setDriverClassName(dbConfigProperties.getProperty("jdbc.driverClassName"));
    dataSource.setUrl("jdbc:mysql://" + DATABASE_IP + ":3306/quickdocument_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    dataSource.setUsername(dbConfigProperties.getProperty("jdbc.username"));
    dataSource.setPassword(dbConfigProperties.getProperty("jdbc.password"));

    return dataSource;

  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {

    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("ch.cloud.quickdocument.service.dam.model.entities");
    sessionFactory.setHibernateProperties(hibernateProperties());
    return sessionFactory;

  }


  @Bean
  public HibernateTransactionManager getTransactionManager() {

    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory().getObject());

    return transactionManager;
  }

  private Properties hibernateProperties() {

    Properties dbConfigProperties = loadDbConfigProperties();

    Properties properties = new Properties();

    properties.put("hibernate.dialect", dbConfigProperties.getProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", dbConfigProperties.getProperty("hibernate.show_sql"));
    properties.put("hibernate.format_sql", dbConfigProperties.getProperty("hibernate.format_sql"));
    properties.put("hibernate.hbm2ddl.auto", dbConfigProperties.getProperty("hibernate.hbm2ddl.auto"));

    return properties;

  }



  private Properties loadDbConfigProperties() {

    // get resource inside resource path
    Resource resource = resourceLoader.getResource("classpath:ormmapping/" + PROJECT_NAME);

    Properties dbConfigProperties = new Properties();

    try {

      // get config file from that received resource
      File file = resource.getFile();

      if (file.isFile()) {

        // read key value db configuration inside file and place it in the properties object
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1)) {

          dbConfigProperties.load(reader);
        }

      }



    } catch (IOException e) {

      LOG.warn("Fail to load messages properties from properties files");

    }

    return dbConfigProperties;

  }

}
