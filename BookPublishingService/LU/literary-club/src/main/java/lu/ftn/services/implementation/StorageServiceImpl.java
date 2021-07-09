package lu.ftn.services.implementation;

import lu.ftn.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class StorageServiceImpl implements StorageService {


    @Override
    public void storeFile(InputStream inputStream, String path, String fileName) throws IOException {
        createFilePathIfNotExisting(path);
        Files.copy(inputStream, Paths.get(path).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public Path storeFile(Path sourcePath, String path, String fileName) throws IOException {
        createFilePathIfNotExisting(path);
        Path targetPath = Paths.get(path).resolve(fileName);
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        return targetPath;
    }

    @Override
    public String storeAsTempFile(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("UPP", ".pdf");
        tempFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(multipartFile.getBytes());
        fos.flush();
        fos.close();
        return tempFile.getAbsolutePath();
    }

    @Override
    public File returnFile(String path) {
        return new File(path);
    }

    @Override
    public Resource returnFileAsResource(String path) throws MalformedURLException, NoSuchFileException {
        Resource resource = new UrlResource(Paths.get(path).toUri());
        if (resource.exists() && resource.isReadable())
            return resource;
        else
            throw new NoSuchFileException(path);
    }

    @Override
    public File returnFilesAsZip(List<String> paths) throws IOException {
        File tempFile = File.createTempFile("UPP", ".zip");
        tempFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(tempFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (String path : paths) {
            addFileToZipArchive(zipOut, path);
        }

        zipOut.flush();
        zipOut.close();
        fos.flush();
        fos.close();

        return tempFile;
    }

    @Override
    public File returnFilesAsZip(String plagiarizedBookPath, String originalBookPath) throws IOException {
        File tempFile = File.createTempFile("UPP", ".zip");
        tempFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(tempFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        addFileToZipArchive(zipOut, plagiarizedBookPath, "plagiarized.pdf");
        addFileToZipArchive(zipOut, originalBookPath, "original.pdf");

        zipOut.flush();
        zipOut.close();
        fos.flush();
        fos.close();

        return tempFile;
    }

    private void addFileToZipArchive(ZipOutputStream zipOut, String path) throws IOException {
        File fileToZip = new File(path);
        FileInputStream fis = new FileInputStream(fileToZip);

        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    private void addFileToZipArchive(ZipOutputStream zipOut, String path, String name) throws IOException {
        File fileToZip = new File(path);
        FileInputStream fis = new FileInputStream(fileToZip);

        ZipEntry zipEntry = new ZipEntry(name);
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    private void createFilePathIfNotExisting(String path) throws IOException {
        if (!Files.exists(Paths.get(path))) {
            Files.createDirectories(Paths.get(path));
        }
    }

}
