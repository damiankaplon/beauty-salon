package pl.damiankaplon.beautyspace.controller.web.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;

import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreatmentForm {
        private String name, shortDescription, fullDescription, minPrice, maxPrice, aproxTime;
        private Set<String> chosenTypes;



        public Float getMinPriceValue() {
                return Float.valueOf(this.minPrice);
        }

        public Float getMaxPriceValue() {
                return Float.valueOf(this.maxPrice);
        }

        public LocalTime getAproxTimeAsLocalTime() {
                return LocalTime.parse(aproxTime);
        }

        public static TreatmentForm empty() {
                return new TreatmentForm();
        }

        public static TreatmentForm filledWith(Treatment treatment) {
                TreatmentForm form = new TreatmentForm();
                form.setMaxPrice(treatment.getMaxPrice().toString());
                form.setMinPrice(treatment.getMinPrice().toString());
                form.setAproxTime(treatment.getAproxTime().toString());
                form.setName(treatment.getName());
                form.setFullDescription(treatment.getFullDescription());
                form.setShortDescription(treatment.getShortDescription());
                return form;
        }
}
