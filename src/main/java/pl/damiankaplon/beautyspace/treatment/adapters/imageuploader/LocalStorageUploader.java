package pl.damiankaplon.beautyspace.treatment.adapters.imageuploader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.treatment.domain.dtos.ImageDto;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.ImageUploader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocalStorageUploader implements ImageUploader {

    private static final String STORAGE_PATH = "/src/main/resources/static/ServicesPictures/";

    public List<ImageDto> upload(List<MultipartFile> pictures) throws IOException {
        List<ImageDto> dtos = new ArrayList<>();
        if (!arePicturesValid(pictures)) return dtos;

        for (MultipartFile picture : pictures) {
            byte[] bytes = picture.getBytes();

            LocalDateTime timestamp = LocalDateTime.now();
            Path pathToSave = Paths.get(
                    Paths.get("").toAbsolutePath()
                            + STORAGE_PATH
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

    private boolean arePicturesValid(List<MultipartFile> pictures) {
        if (pictures == null) return false;
        if (pictures.size() <= 0) return false;
        return !(pictures.stream().allMatch(MultipartFile::isEmpty));
    }
}
