package pl.damiankaplon.beautysaloon.treatment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damiankaplon.beautysaloon.Picture.PictureDto;
import pl.damiankaplon.beautysaloon.controller.form.TreatmentForm;

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
        List<TreatmentDto> dtos = treatments.stream()
                .map(t -> mapper.map(t, TreatmentDto.class))
                .collect(Collectors.toList());
        return dtos;
    }
}
