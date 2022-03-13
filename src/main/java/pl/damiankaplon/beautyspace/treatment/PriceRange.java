package pl.damiankaplon.beautyspace.treatment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
class PriceRange {
    private Float minPrice;
    private Float maxPrice;

}
