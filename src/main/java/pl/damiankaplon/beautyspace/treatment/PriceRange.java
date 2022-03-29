package pl.damiankaplon.beautyspace.treatment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class PriceRange {
    private Float minPrice;
    private Float maxPrice;

    public PriceRange(Float minPrice, Float maxPrice) {
        if (minPrice == null) this.minPrice = 0f;
        else this.minPrice = minPrice;;

        if (maxPrice == null) this.maxPrice = 0f;
        else this.maxPrice = maxPrice;
    }
}
