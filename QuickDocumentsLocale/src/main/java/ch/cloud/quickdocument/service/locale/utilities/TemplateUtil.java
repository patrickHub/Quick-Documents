package ch.cloud.quickdocument.service.locale.utilities;

import java.util.Properties;
import org.jboss.logging.Logger;
import ch.cloud.quickdocument.service.locale.model.beans.MessageFormatterMethod;

/**
 * {@code TemplateUtil} class content useful methods to handle templates when process template file.
 * <p>
 * It contents {@link #resolveVariables(String, Properties, String, String)} method that will resolve any variable that is passed as parameter in {@link MessageFormatterMethod
 * MessageFormatterMethod}
 * </p>
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class TemplateUtil {

  private static final Logger LOG = Logger.getLogger(TemplateUtil.class);


  /**
   * This method will resolve any variable that is passed as parameter in {@link MessageFormatterMethod MessageFormatterMethod} object inside the template file.
   * <p>
   * It will call {@link #resolveVariables(String, Properties, String, String)} method using default "${" as startMarker and "}" as endMarker.
   * </p>
   * <p>
   * It you want to used another marker instead of default one, please use {@link #resolveVariables(String, Properties, String, String)} method.
   * </p>
   * 
   * @param  text  test the variable to be resolve
   * @param  props properties file
   * @return
   */
  public static String resolveVariables(String text, Properties props) {
    return resolveVariables(text, props, "${", "}");
  }

  /**
   * This method will resolve any variable that is passed as parameter in {@link MessageFormatterMethod MessageFormatterMethod} object inside the template file.
   * <p>
   * if the variable is a simple value, it will return the variable, but if that variable content properties key, then it will replace any property key by its corresponding value
   * inside properties file.
   * </p>
   * 
   * @param  text        test the variable to be resolve
   * @param  props       properties file
   * @param  startMarker start delimiter of properties key inside variable
   * @param  endMarker   end delimiter of properties key inside variable
   * @return
   */
  public static String resolveVariables(String text, Properties props, String startMarker, String endMarker) {

    int e = 0;
    int s = text.indexOf(startMarker);

    if (s == -1) {

      // the variable don't any startMarker then it's a simple value and we should return it
      return text;

    } else {
      StringBuilder sb = new StringBuilder();

      // variable contents key properties, then will should find theirs corresponding values and
      // replace them inside variable before returning the variable
      do {
        if (e < s) {
          sb.append(text.substring(e, s));
        }
        e = text.indexOf(endMarker, s + startMarker.length());
        if (e != -1) {
          String key = text.substring(s + startMarker.length(), e);
          sb.append(props.getProperty(key, key));
          e += endMarker.length();
          s = text.indexOf(startMarker, e);
        } else {
          e = s;
          break;
        }
      } while (s != -1);

      if (e < text.length()) {
        sb.append(text.substring(e));
      }
      return sb.toString();
    }
  }

}
