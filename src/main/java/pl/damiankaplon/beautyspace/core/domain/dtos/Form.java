package pl.damiankaplon.beautyspace.core.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
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

    public LocalTime getAproxTimeAsLocalTime() {
        return LocalTime.parse(aproxTime);
    }
}
