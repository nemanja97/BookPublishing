package rs.ac.uns.ftn.uddproject.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

  void checkIfFileIsPDF(Path path) throws IOException;

  Path saveFile(String bookId, MultipartFile file) throws IOException;

  File returnFile(String path);
}
