package ch.cloud.quickdocument.service.locale.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import ch.cloud.quickdocument.service.locale.model.beans.MessageFormatterMethod;
import ch.cloud.quickdocument.service.locale.utilities.PropertiesUtil;

/**
 * {@code LocaleSelectorHandler} class is a wrapper to enable internalization functionality inside application.
 * 
 * It required that templates files are process using {@link freemarker.template.Configuration} language engine.
 * 
 * <p>
 * It also required {@link Locale Locale} that will be used to return the whole messages in the selected language
 * </p>
 * 
 * <p>
 * It contains only {@link #handleLocaleMessages(Locale)} method that will return {@link MessageFormatterMethod MessageFormatterMethod} object containing all the messages key and
 * the message in the selected language above.
 * <P>
 * Later, that {@link MessageFormatterMethod} object will be render as attribute in template in order to replace any message key by its corresponding value when processing the
 * template.
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */

@Component
public class LocaleSelectorHandler {

  @Autowired
  ResourceLoader resourceLoader;

  public final Logger LOG = Logger.getLogger(LocaleSelectorHandler.class);

  private final String staticResourcesDir = "classpath:static/"; // the static resource directory path

  private final String baseBundleName = "messages"; // each properties file start with messages

  /**
   * This method will open the corresponding messages properties file, detect its encoding, read its content and return {@link Properties Properties} containing any key value
   * properties found inside properties file
   * 
   * Each property file is named as follow: messages_(language code).properties where language code is provide by locale parameter
   * 
   * @param  locale      selected locale
   * @return
   * @throws IOException
   */
  public Properties getMessages(Locale locale) {

    if (Objects.isNull(locale)) {
      return null;
    }

    Properties properties = new Properties();

    // get resource inside resource path
    Resource resource = resourceLoader.getResource(staticResourcesDir + baseBundleName + "/" + baseBundleName + "_" + locale.toString() + ".properties");

    try {

      // get file from that received resource
      File file = resource.getFile();

      if (file.isFile()) {

        // get file encoding
        Charset encoding = PropertiesUtil.detectEncoding(new FileInputStream(file));

        // read key value properties inside file and place it in the properties object
        try (Reader reader = new InputStreamReader(new FileInputStream(file), encoding)) {
          properties.load(reader);
        }
      }
    } catch (IOException e) {

      LOG.warn("Fail to load messages properties from properties files");

    }


    return properties;

  }


  /**
   * This method will handle any key value property found inside messages properties file according to the selected locale language, save them in {@link Properties} object and wrap
   * that object in {@link MessageFormatterMethod MessageFormatterMethod} object.
   * <p>
   * Later, that {@link MessageFormatterMethod} object will be render as attribute in template in order to replace any message key by its corresponding value when processing the
   * template.
   * </p>
   * 
   * @param  locale selected locale language
   * @return
   */
  public MessageFormatterMethod handleLocaleMessages(Locale locale) {


    MessageFormatterMethod messageFormatterMethod = null;

    Properties messagesBundle = getMessages(locale);

    messageFormatterMethod = new MessageFormatterMethod(locale, messagesBundle);


    return messageFormatterMethod;


  }

}
