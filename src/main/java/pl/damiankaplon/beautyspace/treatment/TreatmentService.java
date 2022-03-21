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
import java.util.UUID;
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


    public Page<TreatmentDto> geTreatmentsPage(Pageable pageable) {
        treatmentsDb = repo.findAll();
        List<Treatment> treatmentsPage = getTreatmentsForPage(pageable);

        List<TreatmentDto> dtos = treatmentsPage.stream()
                .map(t -> mapper.map(t, TreatmentDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()), treatmentsDb.size());
    }

    private List<Treatment> getTreatmentsForPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currPage = pageable.getPageNumber() - 1;
        int startItem = currPage * pageSize;

        List<Treatment> treatmentsPage;

        if (treatmentsDb.size() < startItem) {
            treatmentsPage = Collections.emptyList();
        }
        else {
            int endIndex = Math.min(startItem + pageSize, treatmentsDb.size());
            treatmentsPage = treatmentsDb.subList(startItem, endIndex);
        }

        return treatmentsPage;
    }

    public TreatmentDto getTreatment(UUID uuid) {
        Treatment treatment = repo.findByUuid(uuid);
        return mapper.map(treatment, TreatmentDto.class);
    }
}
