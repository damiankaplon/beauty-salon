package pl.damiankaplon.beautyspace.treatment.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Set;

@Data
@NoArgsConstructor
public class Form {
    private String name, shortDescription, fullDescription, minPrice, maxPrice, aproxTime;
    private Set<String> chosenTypes;


    public Float getMinPriceValue() {
        return Float.valueOf(this.minPrice);
    }

    public Float getMaxPriceValue() {
        return Float.valueOf(this.maxPrice);
    }

    public Duration getAproxTimeAsDuration() {
        return Duration.ofSeconds(Long.parseLong(this.aproxTime));
    }
}
