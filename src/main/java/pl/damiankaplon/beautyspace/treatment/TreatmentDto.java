package pl.damiankaplon.beautyspace.treatment;

import lombok.Data;

import java.util.UUID;

@Data
public class TreatmentDto {
    private UUID uuid;
    private String name, shortDescription, fullDescription, picturePath;
    private Float aproxTime, minPrice, maxPrice;
}
