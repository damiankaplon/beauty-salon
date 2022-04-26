package pl.damiankaplon.beautyspace.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.treatment.adapters.imageservice.LocalStorageService;
import pl.damiankaplon.beautyspace.treatment.domain.dtos.ImageDto;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.ImageService;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.ImageServiceException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageServiceTests {

    ImageService imageService = new LocalStorageService();

    @Test
    public void returnsImageDtosOfUploadedImagesAndDeletesThemWithNoExceptionLater() throws ImageServiceException {
        //GIVEN
        List<MultipartFile> images = List.of(
                new MockMultipartFile("zaq1wsx.jpg", "zaq2wsx.jpg", null, "randomBytesString".getBytes(StandardCharsets.UTF_8)),
                new MockMultipartFile("zaq2wsx.jpg", "zaq2wsx.jpg", null, "randomBytesString".getBytes(StandardCharsets.UTF_8))
        );

        //WHEN
        List<ImageDto> underTest= imageService.upload(images);

        //THEN
        Assertions.assertThat(underTest).hasSize(2);
        Assertions.assertThatNoException().isThrownBy(() -> imageService.delete(new HashSet<>(underTest)));
    }

    @Test
    public void throwsExceptionWhenDeletingFileWhichNotExists() {

        ImageDto fakeImage = new ImageDto("/ServicePictures/FAKEXD.jpg");

        Assertions.assertThatExceptionOfType(ImageServiceException.class)
                .isThrownBy(() -> imageService.delete(Set.of(fakeImage)));
    }

    @Test
    public void returnsEmptyListWhenNoPicturesToUpload() throws ImageServiceException {
       //WHEN
        List<ImageDto> underTest = imageService.upload(new ArrayList<>());
        //THEN
        Assertions.assertThat(underTest).hasSize(0);
    }

}
