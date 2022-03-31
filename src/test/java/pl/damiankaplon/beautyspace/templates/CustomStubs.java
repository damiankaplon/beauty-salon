package pl.damiankaplon.beautyspace.templates;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.picture.PictureDto;
import pl.damiankaplon.beautyspace.treatment.Treatment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class CustomStubs {

    static List<PictureDto> stubPictureServiceUploadMethod(List<MultipartFile> pictures) throws IOException {
        List<PictureDto> dtos = new ArrayList<>();
        for (MultipartFile picture : pictures) {
            LocalDateTime timestamp = LocalDateTime.now();
            String pathForObjects = "/ServicesPictures/"
                    + timestamp
                    + picture.getOriginalFilename();
            dtos.add(new PictureDto(pathForObjects));
        }
        return dtos;
    }

    public static class StubTreatmentsPage implements Page<Treatment> {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public int getNumberOfElements() {
            return 0;
        }

        @Override
        public List<Treatment> getContent() {
            return null;
        }

        @Override
        public boolean hasContent() {
            return false;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public boolean isFirst() {
            return false;
        }

        @Override
        public boolean isLast() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Pageable nextPageable() {
            return null;
        }

        @Override
        public Pageable previousPageable() {
            return null;
        }

        @Override
        public <U> Page<U> map(Function<? super Treatment, ? extends U> converter) {
            return null;
        }

        @Override
        public Iterator<Treatment> iterator() {
            return null;
        }
    }

}
