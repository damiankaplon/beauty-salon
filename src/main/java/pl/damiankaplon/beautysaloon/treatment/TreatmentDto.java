package pl.damiankaplon.beautysaloon.treatment;

import lombok.Data;

@Data
public class TreatmentDto {
    private String name, shortDescription, fullDescription, picturePath;
    private Float minPrice, maxPrice;
}
