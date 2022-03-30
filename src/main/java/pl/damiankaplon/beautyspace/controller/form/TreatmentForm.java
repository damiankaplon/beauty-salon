package pl.damiankaplon.beautyspace.controller.form;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class TreatmentForm {
        private String name, shortDescription, fullDescription, minPrice, maxPrice, aproxTime;
        private List<String> chosenTypes;

        public LocalTime getAproxTimeAsLocalTime() {
                return LocalTime.parse(aproxTime);
        }
}
