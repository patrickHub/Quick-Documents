package ch.cloud.quickdocument.service.locale.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jboss.logging.Logger;

/**
 * {@code PropertiesUtil} class contents useful methods to handle {@code .properties files}.
 * 
 * <p>It contents {@link #detectEncoding(InputStream)} method that will detect the .properties file encoding
 * that will be provided in {@link InputStreamReader InputStreamReader} class when reading the file.<p>
 *  
 * @Author <a href="pDjomo@hamilton-medical.com">Patrick Djomo</a>
 *
 */
public class PropertiesUtil {
  
  private static final Logger LOG = Logger.getLogger(PropertiesUtil.class);
  
  public static final Pattern DETECT_ENCODING_PATTERN = Pattern.compile("^#\\s*encoding:\\s*([\\w.:-]+)",
      Pattern.CASE_INSENSITIVE);

  public static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");


  
  /**
   * <p>
   * Detect file encoding from the first line of the property file. If the first line in the file doesn't contain the
   * comment with the encoding, it uses ISO-8859-1 as default encoding for backwards compatibility.
   * </p>
   * <p>
   * The specified stream is closed before this method returns.
   * </p>
   * 
   * @param inputStream The input stream
   * @return Encoding
   * @throws IOException
   */
  public static Charset detectEncoding(InputStream inputStream) throws IOException {
    
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, DEFAULT_ENCODING))){
      
      // get first line if any
      String firstLine = br.readLine();
      
      if (firstLine != null) {
        
        // create matcher that will be used to check if the first line matches the encoding pattern
        Matcher matcher = DETECT_ENCODING_PATTERN.matcher(firstLine);
        
        // check if the fist line matches
        if (matcher.find()) {
        
          String encoding = matcher.group(1);
          
          // the first line matches the encoding pattern, then check it's supported by @Charset
          if (Charset.isSupported(encoding)) {
            
            // return the supported  @Charset
            return Charset.forName(encoding);
              
          } else {
            
            LOG.warnv("Unsupported encoding: {0}", encoding);
          }
        }
      }
    }
    
    return DEFAULT_ENCODING;
  }
}
