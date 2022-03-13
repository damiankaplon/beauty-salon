package pl.damiankaplon.beautysaloon.controller.form;

import lombok.Data;

@Data
public class TreatmentForm {
        private String name, shortDescription, fullDescription, minPrice, maxPrice;
}
