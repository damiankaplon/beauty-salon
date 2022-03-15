package pl.damiankaplon.beautyspace.treatment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damiankaplon.beautyspace.picture.PictureDto;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository repo;
    private final ModelMapper mapper = new TreatmentMapper();

    public void addNewTreatment(TreatmentForm form, PictureDto picDto) {
        TreatmentDto dto = mapper.map(form, TreatmentDto.class);
        dto.setPicturePath(picDto.getPathToFile());

        repo.save(Treatment.of(dto));
    }

    public List<TreatmentDto> getAllTreatments() {
        List<Treatment> treatments = repo.findAll();
        return treatments.stream()
                .map(t -> mapper.map(t, TreatmentDto.class))
                .collect(Collectors.toList());
    }
}
