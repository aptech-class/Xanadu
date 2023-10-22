package Xanadu.Utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FilesProcessor {
    private FilesProcessor() {
    }

    private static final Path pathDir;
    private static final Path pathDirInSrc;

    static {
        try {
            pathDir = ResourceUtils.getFile("classpath:static/").toPath();
            pathDirInSrc = Paths.get(String.valueOf(pathDir).replace("target\\classes", "src\\main\\resources"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static String saveFileByDataUrl(String dataUrl, String dir) throws Exception {
        Files.createDirectories(Paths.get(String.valueOf(pathDir), dir));
        Files.createDirectories(Paths.get(String.valueOf(pathDirInSrc), dir));

        String fileType = dataUrl.substring(dataUrl.indexOf(":") + 1, dataUrl.indexOf("/"));
        if (!fileType.equals("image")) {
            throw new Exception("Invalid file format!");
        }
        String extension = dataUrl.substring(dataUrl.indexOf("/") + 1, dataUrl.indexOf(";"));
        String base64Data = dataUrl.replaceAll("^.*base64,", "");
        byte[] data = Base64.decodeBase64(base64Data);
        String fileName = StringProcessor.generateRandomCharacters("1234567890", 20) + "." + extension;
        Path path = Paths.get(String.valueOf(pathDir), dir, fileName);
        Files.write(path, data);
        Path pathInSrc = Paths.get(String.valueOf(pathDirInSrc), dir, fileName);
        Files.write(pathInSrc, data);
        return dir + "/" + fileName;
    }

    public static String saveFileByMultiPart(MultipartFile multipartFile, String dir) throws Exception {
        Files.createDirectories(Paths.get(String.valueOf(pathDir), dir));
        Files.createDirectories(Paths.get(String.valueOf(pathDirInSrc), dir));
        if (multipartFile.isEmpty()) {
            throw new Exception("MultipartFile is empty!");
        }
        String fileName = multipartFile.getOriginalFilename().replaceAll(".*\\.", StringProcessor.generateRandomCharacters("123456789", 20) + ".");
        Path path  = Paths.get(String.valueOf(pathDir),dir,fileName);
        Path pathInSrc  = Paths.get(String.valueOf(pathDirInSrc),dir,fileName);
        multipartFile.transferTo(path);
        multipartFile.transferTo(pathInSrc);
        return dir + "/" + fileName;
    }

    public static void deleteFile(String path) throws IOException {
        Files.deleteIfExists(Paths.get(String.valueOf(pathDir),path));
        Files.deleteIfExists(Paths.get(String.valueOf(pathDirInSrc),path));
    }
}
