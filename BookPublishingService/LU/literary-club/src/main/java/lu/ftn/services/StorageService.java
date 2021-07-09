package lu.ftn.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public interface StorageService {

    void storeFile(InputStream inputStream, String path, String fileName) throws IOException;

    Path storeFile(Path sourcePath, String path, String fileName) throws IOException;

    String storeAsTempFile(MultipartFile multipartFile) throws IOException;

    File returnFile(String path);

    Resource returnFileAsResource(String path) throws MalformedURLException, NoSuchFileException;

    File returnFilesAsZip(List<String> paths) throws IOException;

    File returnFilesAsZip(String plagiarizedBookPath, String originalBookPath) throws IOException;
}
