package pl.damiankaplon.beautyspace.treatment;

import jdk.jfr.Timespan;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID uuid;
    private String name;
    private String shortDescription, fullDescription;
    private LocalTime aproxTime;
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
                .aproxTime(dto.getAproxTime())
                .fullDescription(dto.getFullDescription())
                .picture(new Picture(dto.getPicturePath()))
                .build();
    }

}
