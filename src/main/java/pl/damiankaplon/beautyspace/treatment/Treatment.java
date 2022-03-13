package pl.damiankaplon.beautyspace.treatment;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID uuid;
    private String name;
    private String shortDescription, fullDescription;
    @Embedded
    private PriceRange priceRange;
    @Embedded
    private Picture picture;

    protected Treatment(){}

    static Treatment of(TreatmentDto dto) {
        return Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(dto.getName())
                .priceRange(new PriceRange(Float.valueOf(dto.getMinPrice() + "0"), Float.valueOf(dto.getMaxPrice() + "0")))
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .picture(new Picture(dto.getPicturePath()))
                .build();
    }

}
