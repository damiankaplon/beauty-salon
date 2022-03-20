package pl.damiankaplon.beautyspace.treatment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.damiankaplon.beautyspace.picture.PictureDto;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository repo;
    private final ModelMapper mapper = new TreatmentMapper();
    private List<Treatment> treatmentsDb;

    public TreatmentDto addNewTreatment(TreatmentForm form, PictureDto picDto) {
        TreatmentDto dto = mapper.map(form, TreatmentDto.class);
        dto.setPicturePath(picDto.getPathToFile());

        Treatment treatment = Treatment.of(dto);

        return mapper.map(repo.save(treatment), TreatmentDto.class);
    }

    public List<TreatmentDto> getAllTreatments() {
        treatmentsDb = repo.findAll();
        return treatmentsDb.stream()
                .map(t -> mapper.map(t, TreatmentDto.class))
                .collect(Collectors.toList());
    }

    public Page<TreatmentDto> geTreatmentsPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currPage = pageable.getPageNumber() - 1;
        int startItem = currPage * pageSize;
        List<Treatment> treatmentsPage;

        treatmentsDb = repo.findAll();
        if (treatmentsDb.size() < startItem) {
            treatmentsPage = Collections.emptyList();
        }
        else {
            int endIndex = Math.min(startItem + pageSize, treatmentsDb.size());
            treatmentsPage = treatmentsDb.subList(startItem, endIndex);
        }

        List<TreatmentDto> dtos = treatmentsPage.stream()
                .map(t -> mapper.map(t, TreatmentDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, PageRequest.of(currPage, pageSize), treatmentsDb.size());
    }
}
