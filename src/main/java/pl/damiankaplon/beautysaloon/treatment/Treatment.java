package pl.damiankaplon.beautysaloon.treatment;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Treatment {
    private UUID uuid;
    private String name;
    private PriceRange priceRange;
    private String shortDescription, fullDescription;
    private Picture picture;

    private Treatment(UUID uuid, String name, PriceRange priceRange, String shortDescription, String fullDescription, Picture picture) {
        this.uuid = uuid;
        this.name = name;
        this.priceRange = priceRange;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.picture = picture;
    }

    static Treatment of(TreatmentDto dto) {
        return Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(dto.getName())
                .priceRange(new PriceRange(Float.valueOf(dto.getFrom() + "0"), Float.valueOf(dto.getTo() + "0")))
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .build();
    }

}
