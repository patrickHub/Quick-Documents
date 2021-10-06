package ch.cloud.quickdocument.service.rest.utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ch.cloud.quickdocument.service.rest.configuration.Constants;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class FileImageUtil {

  public static String UPLOAD_DIR = "/quick-directory-images/";

  private FileImageUtil() {};

  private static String generateImageName(String imageFormat) {


    File file = null;
    String newName = null;

    do {

      newName = Constants.IMAGE + "_" + Utility.getCurrentInCustomFormat().format(Utility.getCurrentInCustomFormat().getCalendar().getTime()) + "_"
          + getRandomValue() + imageFormat;

      file = new File(UPLOAD_DIR + newName);

    } while (file.exists());

    return newName;

  }

  private static int getRandomValue() {

    Random rand = new Random();
    return rand.nextInt(100000);

  }

  public static Path saveImageInFileSystem(MultipartFile imageFile) throws IOException {

    String originalName = StringUtils.cleanPath(imageFile.getOriginalFilename());

    String imageFormat = originalName.substring(originalName.lastIndexOf("."));

    String fileName = generateImageName(imageFormat);

    Path path = Paths.get(UPLOAD_DIR);

    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }

    InputStream inputStream = imageFile.getInputStream();

    Path filePath = path.resolve(fileName);

    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

    return filePath;



  }

  public static String readTextFromImage(Path filePath) throws TesseractException {

    ITesseract instance = new Tesseract();

    return instance.doOCR(filePath.toFile());
  }

}
