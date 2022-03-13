package pl.damiankaplon.beautyspace.controller.form;

import lombok.Data;

@Data
public class TreatmentForm {
        private String name, shortDescription, fullDescription, minPrice, maxPrice;
}
