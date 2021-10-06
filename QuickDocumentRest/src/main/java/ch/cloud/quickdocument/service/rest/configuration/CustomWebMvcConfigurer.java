package ch.cloud.quickdocument.service.rest.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver;
import ch.cloud.quickdocument.service.rest.filter.RequestResponseLoggingFilter;
import ch.cloud.quickdocument.service.rest.utilities.FileImageUtil;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {



  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    resolvers.add(new RequestAttributeMethodArgumentResolver());

  }



  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    Path uploadDir = Paths.get(FileImageUtil.UPLOAD_DIR);

    registry.addResourceHandler("/" + Constants.FILES + "/**").addResourceLocations(Constants.FILE + ":///" + uploadDir.toFile().getAbsolutePath() + "/");

  }

  @Bean
  public FilterRegistrationBean<RequestResponseLoggingFilter> requestResponseLoggingFilter() {

    FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new RequestResponseLoggingFilter());
    registrationBean.addUrlPatterns("/*");

    return registrationBean;
  }

}
