package pl.damiankaplon.beautyspace.core.adapters.imageuploader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.core.domain.dtos.ImageDto;
import pl.damiankaplon.beautyspace.core.domain.ports.outgoing.ImageUploader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocalStorageUploader implements ImageUploader {
    public List<ImageDto> upload(List<MultipartFile> pictures) throws IOException {
        List<ImageDto> dtos = new ArrayList<>();
        if (pictures == null || pictures.size() == 0) return dtos;

        for (MultipartFile picture : pictures) {
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

            dtos.add(new ImageDto(pathForObjects));
        }

        return dtos;
    }
}
