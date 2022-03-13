package pl.damiankaplon.beautyspace.Picture;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class PictureService {

    public PictureDto upload(MultipartFile picture) throws IOException {
        byte[] bytes = picture.getBytes();

        LocalDateTime timestamp = LocalDateTime.now();
        Path pathToSave = Paths.get(
                Paths.get("").toAbsolutePath()
                + "/src/main/resources/static/ServicesPictures/"
                + timestamp
                + picture.getOriginalFilename());
        Files.write(pathToSave, bytes);

        String pathForObjects = "/ServicesPictures/"
                        + timestamp
                        + picture.getOriginalFilename();
        return new PictureDto(pathForObjects);
    }
}
