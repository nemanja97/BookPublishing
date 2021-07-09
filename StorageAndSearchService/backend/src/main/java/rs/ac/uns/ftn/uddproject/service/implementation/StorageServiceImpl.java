package rs.ac.uns.ftn.uddproject.service.implementation;

import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uddproject.service.StorageService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {

  @Value("${fileStorageLocation}")
  private String fileStorageLocation;

  @Override
  public void checkIfFileIsPDF(Path path) throws IOException {
    File fileToCheck = returnFile(path.toString());
    PreflightParser parser = new PreflightParser(fileToCheck);

    parser.parse();
    parser.getPreflightDocument().validate();
    parser.getPreflightDocument().close();
  }

  @Override
  public Path saveFile(String bookId, MultipartFile file) throws IOException {
    createBookStorageDirectoryIfNotExists();
    Path destinationFile =
        Paths.get(String.format("%s/books", fileStorageLocation))
            .resolve(Paths.get(String.format("%s.pdf", bookId)))
            .normalize()
            .toAbsolutePath();
    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      return destinationFile.toAbsolutePath();
    }
  }

  private void createBookStorageDirectoryIfNotExists() throws IOException {
    if (!Files.exists(Paths.get(String.format("%s/books", fileStorageLocation))))
      Files.createDirectories(Paths.get(String.format("%s/books", fileStorageLocation)));
  }

  @Override
  public File returnFile(String path) {
    return new File(path);
  }
}
