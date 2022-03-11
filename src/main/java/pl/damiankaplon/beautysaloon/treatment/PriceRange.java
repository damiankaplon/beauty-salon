package pl.damiankaplon.beautysaloon.treatment;

import lombok.Value;

@Value
class PriceRange {
    Float from;
    Float to;

    PriceRange(Float from, Float to) {
        this.from = from;
        this.to = to;
    }
}
