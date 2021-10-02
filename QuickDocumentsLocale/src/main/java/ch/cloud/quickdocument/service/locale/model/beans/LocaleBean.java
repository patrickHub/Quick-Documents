package ch.cloud.quickdocument.service.locale.model.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.jboss.logging.Logger;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ch.cloud.quickdocument.service.locale.model.entities.SupportedLocale;

/**
 * {@code LocaleBean} class is a wrapper class that will be send as locale attribute in {@link r FreeMarkerPageHandler} class before processing any template. when to enable
 * internalization functionality inside application.
 * 
 * <p>
 * It contains the current locale language selected by user as well as its tag and list of supported languages provided by {@link SupportedLocale SupportedLocale} class.
 * </p>
 * 
 * <p>
 * Those values we will be available on each template to enable user to switch from one language to another on the same page. Its will also be used to download files in the user
 * selected language.
 * </p>
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class LocaleBean {

  private String current;
  private String currentLanguageTag;
  private List<LocaleDTO> supported;

  private final Logger LOG = Logger.getLogger(LocaleBean.class);


  /**
   * {@code LocaleBean} class is a wrapper class that will be send as locale attribute in {@link r FreeMarkerPageHandler} class before processing any template.
   * 
   * <p>
   * It contains the current locale language selected by user as well as its tag and list of supported languages provided by {@link SupportedLocale SupportedLocale} class.
   * </p>
   * 
   * <p>
   * Those values we will be available on each template to enable user to switch from one language to another on the same page. Its will also be used to download files in the user
   * selected language.
   * </p>
   * 
   * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
   *
   */
  public LocaleBean(Locale current, Properties messages, UriComponents urlComponents) {

    this.currentLanguageTag = current.toLanguageTag();

    this.current = messages.getProperty("locale_" + currentLanguageTag);

    supported = new ArrayList<>();

    String languageTag;
    String label;
    String url;
    for (SupportedLocale supportedLocale : SupportedLocale.SUPPORTED_LOCALES) {

      languageTag = supportedLocale.getLocale();
      label = messages.getProperty("locale_" + supportedLocale.getLocale());
      url = UriComponentsBuilder.fromUriString(urlComponents.toUriString()).query("qd_locale={language_tag}").buildAndExpand(languageTag).toUriString();

      supported.add(new LocaleDTO(languageTag, label, url));

    }
  }


  public String getCurrent() {
    return current;
  }


  public String getCurrentLanguageTag() {
    return currentLanguageTag;
  }


  public List<LocaleDTO> getSupported() {
    return supported;
  }



  public static class LocaleDTO {

    private String languageTag;
    private String label;
    private String url;

    public LocaleDTO(String languageTag, String label, String url) {
      this.languageTag = languageTag;
      this.label = label;
      this.url = url;
    }

    public String getLanguageTag() {
      return languageTag;
    }

    public String getUrl() {
      return url;
    }

    public String getLabel() {
      return label;
    }

  }



}
