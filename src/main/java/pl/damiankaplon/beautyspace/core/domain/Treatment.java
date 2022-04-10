package pl.damiankaplon.beautyspace.core.domain;

import lombok.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class Treatment {
    @Getter  private UUID uuid;
    @Getter private String name;
    @Getter private String shortDescription, fullDescription;
    @Getter private Duration aproxTime;
    private PriceRange priceRange;
    private Set<TreatmentType> types;
    private Set<Image> images;


    public Float getMinPrice(){
        return this.priceRange.getMinPrice();
    }

    public Float getMaxPrice(){
        return this.priceRange.getMaxPrice();
    }

    public Set<String> getTypesNames() {
        if (this.types == null) return new HashSet<>();
        return this.types.stream()
                .map(TreatmentType::getBodyPartName)
                .collect(Collectors.toSet());
    }

    public List<String> getImagesSrcs() {
        if (this.types == null) return new ArrayList<>();
        return this.images.stream()
                .map(Image::getSrc)
                .collect(Collectors.toList());
    }

    public static class TreatmentBuilder {
        private PriceRange priceRange;
        private Set<Image> images;
        private Set<TreatmentType> types;

        public TreatmentBuilder priceRange(Float min, Float max) {
            this.priceRange = new PriceRange(min, max);
            return this;
        }

        public TreatmentBuilder images(Set<String> srcs) {
            this.images = srcs.stream()
                    .map(Image::new)
                    .collect(Collectors.toSet());
            return this;
        }

        public TreatmentBuilder types(Set<String> types) {
            this.types = types.stream()
                    .map(TreatmentType::fromString)
                    .collect(Collectors.toSet());
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treatment other = (Treatment) o;
        return uuid.equals(other.getUuid())
                && name.equals(other.getName())
                && shortDescription.equals(other.getShortDescription())
                && fullDescription.equals(other.getFullDescription())
                && aproxTime.equals(other.getAproxTime())
                && getImagesSrcs().equals(other.getImagesSrcs())
                && getTypesNames().equals(other.getTypesNames())
                && getMinPrice().equals(other.getMinPrice())
                && getMaxPrice().equals(other.getMaxPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, shortDescription, fullDescription, aproxTime, priceRange, types, images);
    }
}
