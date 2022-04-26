package pl.damiankaplon.beautyspace.treatment.adapters.imageservice;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.treatment.domain.dtos.ImageDto;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.ImageService;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.ImageServiceException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class LocalStorageService implements ImageService {

    private static final String STORAGE_PATH = "/src/main/resources/static/ServicesPictures/";

    public List<ImageDto> upload(List<MultipartFile> pictures) throws ImageServiceException {
        try {
            return saveMultipartFilesOnDisk(pictures);
        } catch (IOException ex) {
            throw new ImageServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private List<ImageDto> saveMultipartFilesOnDisk(List<MultipartFile> pictures) throws IOException {
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

    @Override
    public void delete(Set<ImageDto> images) throws ImageServiceException {
        for (ImageDto image : images) {
            deletePicture(image);
        }
    }
    private void deletePicture(ImageDto image) throws ImageServiceException {
        Path pathToPicture = Paths.get(
                Paths.get("").toAbsolutePath()
                + STORAGE_PATH
                + image.getPathToFile().substring("/ServicePictures/".length())
        );
        try {
            Files.delete(pathToPicture);
        } catch (IOException e) {
            throw new ImageServiceException(e.getMessage(), e.getCause());
        }
    }

    private boolean arePicturesValid(List<MultipartFile> pictures) {
        if (pictures == null) return false;
        if (pictures.size() <= 0) return false;
        return !(pictures.stream().allMatch(MultipartFile::isEmpty));
    }
}
