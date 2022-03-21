package pl.damiankaplon.beautyspace.controller.form;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TreatmentForm {
        private String name, shortDescription, fullDescription, minPrice, maxPrice, aproxTime;

        public LocalTime getAproxTimeAsLocalTime() {
                return LocalTime.parse(aproxTime);
        }
}
