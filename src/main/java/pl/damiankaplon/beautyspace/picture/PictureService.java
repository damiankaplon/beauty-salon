package pl.damiankaplon.beautyspace.picture;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureService {

    public List<PictureDto> upload(List<MultipartFile> pictures) throws IOException {
        List<PictureDto> dtos = new ArrayList<>();
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

            dtos.add(new PictureDto(pathForObjects));
        }

        return dtos;
    }
}
