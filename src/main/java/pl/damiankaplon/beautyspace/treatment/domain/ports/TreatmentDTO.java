package pl.damiankaplon.beautyspace.treatment.domain.ports;

import lombok.Data;

import java.util.List;

@Data
public class TreatmentDTO {
    String name, shortDescription, fullDescription, minPrice, maxPrice, aproxTime;
    List<String> chosenTypes;
    List<String> imageSrcs;
}
