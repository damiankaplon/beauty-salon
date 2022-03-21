package pl.damiankaplon.beautyspace.treatment;

import lombok.*;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;

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

    static Treatment of(TreatmentForm form, Picture picture) {
        return Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(form.getName())
                .priceRange(new PriceRange(Float.valueOf(form.getMinPrice() + "0"), Float.valueOf(form.getMaxPrice() + "0")))
                .shortDescription(form.getShortDescription())
                .aproxTime(form.getAproxTimeAsLocalTime())
                .fullDescription(form.getFullDescription())
                .picture(picture)
                .build();
    }

}
