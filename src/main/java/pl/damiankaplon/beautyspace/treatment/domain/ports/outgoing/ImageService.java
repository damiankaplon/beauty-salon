package pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing;

import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.treatment.domain.dtos.ImageDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ImageService {
    List<ImageDto> upload(List<MultipartFile> pictures) throws ImageServiceException;
    void delete(Set<ImageDto> images) throws ImageServiceException;
}
