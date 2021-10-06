package ch.cloud.quickdocument.service.rest.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;

/**
 *  
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class RequestResponseLoggingFilter implements Filter{
  
  private final Logger LOG = Logger.getLogger(RequestResponseLoggingFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
   
    chain.doFilter(request, response);
    
    HttpServletRequest httlRequest = (HttpServletRequest)request;
    HttpServletResponse httpResponse = (HttpServletResponse)response;
    
    LOG.info(String.format("Request method=%s, uri=%s, response status=%d", httlRequest.getMethod(), httlRequest.getRequestURL(), httpResponse.getStatus()));
    
  }

}
