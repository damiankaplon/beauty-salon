package pl.damiankaplon.beautyspace.treatment.domain;

import lombok.*;
import org.springframework.security.core.parameters.P;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class Treatment {
    @Getter  private UUID uuid;
    @Getter private String name;
    @Getter private String shortDescription, fullDescription;
    @Getter private LocalTime aproxTime;
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
        return this.types.stream()
                .map(TreatmentType::getBodyPartName)
                .collect(Collectors.toSet());
    }

    public List<String> getImagesSrcs() {
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

}
