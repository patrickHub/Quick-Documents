package ch.cloud.quickdocument.service.mvc.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.UriComponents;
import ch.cloud.quickdocument.service.locale.handler.LocaleSelectorHandler;
import ch.cloud.quickdocument.service.locale.model.beans.LocaleBean;
import ch.cloud.quickdocument.service.mvc.model.beans.UrlBean;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
@Component
@RequestScope
public class FreeMarkerPageHandler {

  private final Logger LOG = Logger.getLogger(FreeMarkerPageHandler.class);

  private Configuration freeMarkerConfig;

  @Autowired
  private LocaleSelectorHandler localeSelectorHandler;

  private Locale locale;

  private UriComponents uriComponents;

  private Map<String, Object> attributes = new HashMap<>();

  public FreeMarkerPageHandler() {

    freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_0);
    freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/");

    LOG.info("Initialize template configuaration");
  }


  /**
   * 
   * @param  templateName
   * @return
   */
  public String processTemplate(String templateName) {

    createCommonAttributes();

    StringBuffer content = new StringBuffer();

    try {

      content.append(FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfig.getTemplate(templateName), attributes));

    } catch (TemplateNotFoundException e) {

      LOG.error("TemplateNotFoundException occured", e);

    } catch (MalformedTemplateNameException e) {

      LOG.error("MalformedTemplateNameException occured", e);

    } catch (ParseException e) {

      LOG.error("ParseException occured", e);

    } catch (IOException e) {

      LOG.error("IOException occured", e);

    } catch (TemplateException e) {

      LOG.error("TemplateException occured", e);

    }

    return content.toString();

  }

  public void addAttribute(String name, Object object) {
    attributes.put(name, object);
  }

  public void removeAttribute(String name) {
    attributes.remove(name);
  }

  private void createCommonAttributes() {

    attributes.put("msg", localeSelectorHandler.handleLocaleMessages(locale));
    attributes.put("url", new UrlBean());
    attributes.put("locale", new LocaleBean(locale, localeSelectorHandler.getMessages(locale), uriComponents));

  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setUriComponents(UriComponents uriComponents) {
    this.uriComponents = uriComponents;
  }



}
