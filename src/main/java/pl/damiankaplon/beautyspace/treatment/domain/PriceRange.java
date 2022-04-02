package pl.damiankaplon.beautyspace.treatment.domain;

import lombok.Value;

@Value
class PriceRange {
    Float minPrice;
    Float maxPrice;

    public PriceRange(Float minPrice, Float maxPrice) {
        if (minPrice == null) this.minPrice = 0f;
        else this.minPrice = minPrice;

        if (maxPrice == null) this.maxPrice = 0f;
        else this.maxPrice = maxPrice;
    }
}
