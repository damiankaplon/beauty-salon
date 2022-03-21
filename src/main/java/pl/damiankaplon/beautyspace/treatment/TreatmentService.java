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


@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository repo;
    private final ModelMapper mapper;

    private List<Treatment> treatmentsDb;

    public Treatment addNewTreatment(TreatmentForm form, PictureDto picDto) {
        Treatment treatment = Treatment.of(form, mapper.map(picDto, Picture.class));
        return repo.save(treatment);
    }


    public Page<Treatment> geTreatmentsPage(Pageable pageable) {
        treatmentsDb = repo.findAll();
        List<Treatment> treatmentsPage = getTreatmentsForPage(pageable);
        return new PageImpl<>(treatmentsPage, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), treatmentsDb.size());
    }

    private List<Treatment> getTreatmentsForPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currPage = pageable.getPageNumber();
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

    public Treatment getTreatment(UUID uuid) {
        return repo.findByUuid(uuid);
    }
}
