package pl.damiankaplon.beautysaloon.treatment;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Value
class Picture {

    UUID uuid;
    String path;
    String filename;

    public static Picture upload(MultipartFile uploadPic) throws IOException {
        String path = Paths.get(".").toAbsolutePath() + "/src/main/resources/static/ServicesPictures/";
        byte[] bytes = uploadPic.getBytes();
        Files.write(Paths.get(path + uploadPic.getOriginalFilename()), bytes);

        return new Picture(UUID.randomUUID(), path, uploadPic.getOriginalFilename());
    }
}
