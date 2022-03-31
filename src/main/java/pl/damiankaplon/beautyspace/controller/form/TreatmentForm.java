package pl.damiankaplon.beautyspace.controller.form;

import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class TreatmentForm {
        private String name, shortDescription, fullDescription, minPrice, maxPrice, aproxTime;
        private Set<String> chosenTypes;

        public Float getMinPrice() {
                return Float.valueOf(this.minPrice);

        }

        public Float getMaxPrice() {
                return Float.valueOf(this.maxPrice);
        }

        public LocalTime getAproxTimeAsLocalTime() {
                return LocalTime.parse(aproxTime);
        }
}
