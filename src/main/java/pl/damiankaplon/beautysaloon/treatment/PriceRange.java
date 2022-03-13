package pl.damiankaplon.beautysaloon.treatment;

import javax.persistence.Embeddable;

@Embeddable
class PriceRange {
    private Float min;
    private Float max;

    //Only for JPA
    protected PriceRange(){}

    PriceRange(Float from, Float to) {
        this.min = from;
        this.max = to;
    }
}
