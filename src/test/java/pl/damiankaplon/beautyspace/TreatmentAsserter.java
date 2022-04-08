package pl.damiankaplon.beautyspace;

import org.junit.jupiter.api.Assertions;
import pl.damiankaplon.beautyspace.core.domain.Treatment;

public class TreatmentAsserter {
    public static void assertEquals(Treatment expected, Treatment actual) {
        Assertions.assertEquals(actual.getUuid(), actual.getUuid());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getShortDescription(), actual.getShortDescription());
        Assertions.assertEquals(expected.getFullDescription(), actual.getFullDescription());
        Assertions.assertEquals(expected.getAproxTime().toString(), actual.getAproxTime().toString());
        Assertions.assertEquals(expected.getTypesNames(), actual.getTypesNames());
        Assertions.assertEquals(expected.getMinPrice(), actual.getMinPrice());
        Assertions.assertEquals(expected.getMaxPrice(), actual.getMaxPrice());
    }
}
