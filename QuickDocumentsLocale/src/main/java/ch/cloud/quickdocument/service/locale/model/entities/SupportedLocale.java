package ch.cloud.quickdocument.service.locale.model.entities;

import java.util.Arrays;
import java.util.List;

/**
 * <code>SupportedLocale</code> object represents one of the languages that is supported in QuickDocument project You can get language code using the method {@link #getLocale()}
 * from the object.
 * <p>
 * Any supported language instantiate as static final, therefore this class can not be instantiated
 * </p>
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class SupportedLocale {

  public static final SupportedLocale ENGLISH = new SupportedLocale("en");
  public static final SupportedLocale FRENCH = new SupportedLocale("fr");
  public static final SupportedLocale GERMAN = new SupportedLocale("de");
  public static final SupportedLocale ITALIAN = new SupportedLocale("it");

  /*
   * limit supperted_locales list to English as the app is released only in English In next released, we will add other languages in supported_locales list in order to enable its
   * into the app
   */
  public static final List<SupportedLocale> SUPPORTED_LOCALES = Arrays.asList(ENGLISH, FRENCH, GERMAN, ITALIAN);

  private final String code;

  private SupportedLocale(String code) {
    this.code = code;
  }

  public String getLocale() {
    return code;
  }

  /**
   * This method will return the {@link SupportedLocale @SupportedLocale} which has language code that matches code parameter
   * <p>
   * if any supportedLocale language code that matches code parameter is not found then it will return {@link #ENGLISH}
   * 
   * @param  code language code
   * @return
   */
  public static SupportedLocale getSupportedLocale(String code) {

    if (ENGLISH.code.equalsIgnoreCase(code)) {
      return ENGLISH;
    }

    if (FRENCH.code.equalsIgnoreCase(code)) {
      return FRENCH;
    }

    if (GERMAN.code.equalsIgnoreCase(code)) {
      return GERMAN;
    }

    if (ITALIAN.code.equalsIgnoreCase(code)) {
      return ITALIAN;
    }

    return ENGLISH;

  }

}
