package Xanadu.Utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FilesProcessor {
    private FilesProcessor() {
    }

    private final static String basePath = "/files/images/products";
    private static final Path pathDir;
    private static final Path pathDirSrc;

    static {
        try {
            Path staticFolder = ResourceUtils.getFile("classpath:static/").toPath();
            Path newDirSrc = Paths.get(String.valueOf(staticFolder).replace("target\\classes", "src\\main\\resources"), basePath);
            Path  newDir = Paths.get(String.valueOf(staticFolder));
            Files.createDirectories(newDir);
            Files.createDirectories(newDirSrc);
            pathDirSrc = newDirSrc;
            pathDir = newDir;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String saveFileByDataUrl(String dataUrl) throws Exception {
        String fileType = dataUrl.substring(dataUrl.indexOf(":") + 1, dataUrl.indexOf("/"));
        if (!fileType.equals("image")) {
            throw new Exception("Invalid file format!");
        }
        String extension = dataUrl.substring(dataUrl.indexOf("/") + 1, dataUrl.indexOf(";"));
        String base64Data = dataUrl.replaceAll("^.*base64,", "");
        byte[] data = Base64.decodeBase64(base64Data);
        String fileName = StringProcessor.generateRandomCharacters("1234567890", 20) + "." + extension;
        Path path = pathDir.resolve(fileName);
        Files.write(path, data);
        Path pathSrc = pathDirSrc.resolve(fileName);
        Files.write(pathSrc,data);
        return basePath + "/" + fileName;
    }
    public static void deleteFile (String srcUrl) throws IOException {
        String fileName = srcUrl.replaceAll(".*/","");
        Files.deleteIfExists(pathDir.resolve(fileName));
        Files.deleteIfExists(pathDirSrc.resolve(fileName));
    }
}
