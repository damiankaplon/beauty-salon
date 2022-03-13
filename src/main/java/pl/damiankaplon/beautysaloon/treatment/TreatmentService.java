package pl.damiankaplon.beautysaloon.treatment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damiankaplon.beautysaloon.Picture.PictureDto;
import pl.damiankaplon.beautysaloon.controller.form.TreatmentForm;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository repo;
    private final ModelMapper mapper;

    public void addNewTreatment(TreatmentForm form, PictureDto picDto) {
        TreatmentDto dto = mapper.map(form, TreatmentDto.class);
        dto.setPicturePath(picDto.getPathToFile());

        repo.save(Treatment.of(dto));
    }
}
