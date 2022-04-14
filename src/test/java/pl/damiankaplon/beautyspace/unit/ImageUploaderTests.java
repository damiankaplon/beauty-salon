package pl.damiankaplon.beautyspace.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.treatment.adapters.imageuploader.LocalStorageUploader;
import pl.damiankaplon.beautyspace.treatment.domain.dtos.ImageDto;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.ImageUploader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ImageUploaderTests {

    ImageUploader imageUploader = new LocalStorageUploader();

    @Test
    public void returnsImageDtosOfUploadedImages() throws IOException {
        //GIVEN
        List<MultipartFile> images = List.of(
                new MockMultipartFile("zaq1wsx.jpg", "randomBytesString".getBytes(StandardCharsets.UTF_8)),
                new MockMultipartFile("zaq2wsx.jpg", "randomBytesString".getBytes(StandardCharsets.UTF_8))
        );

        //WHEN
        List<ImageDto> underTest= imageUploader.upload(images);

        //THEN
        Assertions.assertThat(underTest).hasSize(2);
    }

    @Test
    public void returnsEmptyListWhenNoPicturesToUpload() throws IOException {
       //WHEN
        List<ImageDto> underTest = imageUploader.upload(new ArrayList<>());
        //THEN
        Assertions.assertThat(underTest).hasSize(0);
    }
}
