package pl.damiankaplon.beautyspace;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@NoArgsConstructor
@AllArgsConstructor
class TreatmentSupplier {

    private Supplier<Treatment> supplier;

    private Faker faker = new Faker();

    public Treatment testTreatmentWithUuid(UUID uuid) {
        return Treatment.builder()
                .uuid(uuid)
                .name("test")
                .shortDescription("short Description")
                .fullDescription(faker.lorem().characters(2000))
                .priceRange(100f, 120f)
                .types(Set.of("Face"))
                .images(Set.of("test.img"))
                .aproxTime(LocalTime.parse("02:00"))
                .build();
    }

}
