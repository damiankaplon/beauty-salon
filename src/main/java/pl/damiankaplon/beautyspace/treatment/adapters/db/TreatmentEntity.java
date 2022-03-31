package pl.damiankaplon.beautyspace.treatment.adapters.db;

import lombok.*;
import org.hibernate.Hibernate;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
class TreatmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID uuid;
    private String name;
    private String shortDescription, fullDescription;
    private LocalTime aproxTime;
    private Float minPrice, maxPrice;

    @ElementCollection(targetClass = TreatmentType.class)
    @Enumerated(EnumType.STRING)
    private Set<TreatmentType> types;

    @OneToMany(mappedBy = "treatment")
    @ToString.Exclude
    private Set<Image> pictures;

    static TreatmentEntity of(Treatment treatment) {
        TreatmentEntity entity = new TreatmentEntity();

        entity.setUuid(treatment.getUuid());
        entity.setName(treatment.getName());
        entity.setShortDescription(treatment.getShortDescription());
        entity.setFullDescription(treatment.getFullDescription());
        entity.setAproxTime(treatment.getAproxTime());
        entity.setMinPrice(treatment.getMinPrice());
        entity.setMaxPrice(treatment.getMaxPrice());

        return entity;
    }

    static Treatment to(TreatmentEntity entity) {
        return Treatment.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .shortDescription(entity.getShortDescription())
                .fullDescription(entity.getFullDescription())
                .aproxTime(entity.getAproxTime())
                .priceRange(entity.getMinPrice(), entity.getMaxPrice())
                .pictures(
                        entity.getPictures().stream()
                        .map(Image::getSrc)
                        .collect(Collectors.toSet())
                )
                .types(
                        entity.getTypes().stream()
                        .map(TreatmentType::getBodyPartName)
                        .collect(Collectors.toSet())
                )
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TreatmentEntity entity = (TreatmentEntity) o;
        return id != null && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}