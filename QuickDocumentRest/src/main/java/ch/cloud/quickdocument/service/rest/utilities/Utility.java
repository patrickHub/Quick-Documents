package ch.cloud.quickdocument.service.rest.utilities;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * 
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class Utility {

  private Utility() {}

  public static boolean isLocalDeployment() {
    if (null != System.getenv("QUICKDOCUMENT_PRODUCTION_SERVER") && "1".equals(System.getenv("QUICKDOCUMENT_PRODUCTION_SERVER"))) {
      return false;
    }
    return true;
  }


  public static boolean isJUnitTest() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    List<StackTraceElement> list = Arrays.asList(stackTrace);
    for (StackTraceElement element : list) {
      if (element.getClassName().startsWith("org.junit.")) {
        return true;
      }
    }
    return false;
  }


  public static SimpleDateFormat getCurrentInCustomFormat() {

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setCalendar(calendar);
    return dateFormat;

  }
}
