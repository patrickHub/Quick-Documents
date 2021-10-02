package ch.cloud.quickdocument.service.locale.model.beans;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import ch.cloud.quickdocument.service.locale.utilities.TemplateUtil;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;


/**
 * This class implements {@link TemplateMethodModelEx TemplateMethodModelEx} interface in order to act like function with argument when it is render inside template file.
 * 
 * <p>
 * It used the {@link Properties Properties} object containing {@code messages.properties} file and the choosing {@link Locale Locale} object to replace any properties key inside
 * the template file by its corresponding value in the the choosing language.
 * <p>
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class MessageFormatterMethod implements TemplateMethodModelEx {

  private final Properties messages;
  private final Locale locale;

  /**
   * Unique Constructor to construct {@link MessageFormatterMethod MessageFormatterMethod} objects.
   * 
   * @param locale   the selected locale
   * @param messages properties file messages
   */
  public MessageFormatterMethod(Locale locale, Properties messages) {
    this.locale = locale;
    this.messages = messages;
  }

  @Override
  public Object exec(List list) throws TemplateModelException {

    // check if the list contains any variable or argument that should be resolve
    if (list.size() >= 1) {

      // by default any expression inside any variable start with "&{" and end with "}"
      // resolve any remaining ${} expressions
      List<Object> resolved = resolve(list.subList(1, list.size()));

      // get properties key, for the first argument in the list is always the key of the text
      // that need to be formated with remaining arguments
      String key = list.get(0).toString();

      // format and return the final text that should be display in the template file
      return new MessageFormat(messages.getProperty(key, key), locale).format(resolved.toArray());

    } else {

      return null;
    }
  }

  /**
   * This method resolve any variable that is passed as argument when render the instances of this class in template file
   * 
   * for each variable, it will resolve any ${} expression inside it if any and return that the final text
   * 
   * @param  list list of arguments
   * @return
   */
  private List<Object> resolve(List<Object> list) {

    ArrayList<Object> result = new ArrayList<>();

    for (Object item : list) {

      // for each variable, we need to check if it is an instance of SimpleScalar class
      // in order to able to resolve it when it be used as string
      if (item instanceof SimpleScalar) {

        item = ((SimpleScalar) item).getAsString();
      }

      if (item instanceof String) {

        // resolve any variable
        result.add(TemplateUtil.resolveVariables((String) item, messages));

      } else {
        // the variable is a SimpleScalar Object but not a String, therefore don't need to be resolve
        result.add(item);
      }
    }
    return result;
  }

}
