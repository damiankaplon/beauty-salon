package pl.damiankaplon.beautyspace.treatment;

import com.google.common.collect.Lists;
import jdk.jfr.Timespan;
import lombok.*;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
    @ElementCollection(targetClass = TreatmentBodyPart.class)
    @Enumerated(EnumType.STRING)
    private List<TreatmentBodyPart> bodyParts;
    @ElementCollection(targetClass = Picture.class)
    @Enumerated(EnumType.STRING)
    private List<Picture> pictures;

    protected Treatment(){}

    static Treatment of(TreatmentForm form, List<Picture> picture) {
        return Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(form.getName())
                .priceRange(new PriceRange(Float.valueOf(form.getMinPrice()), Float.valueOf(form.getMaxPrice())))
                .shortDescription(form.getShortDescription())
                .aproxTime(form.getAproxTimeAsLocalTime())
                .fullDescription(form.getFullDescription())
                .pictures(Lists.newArrayList(picture))
                .build();
    }

}
