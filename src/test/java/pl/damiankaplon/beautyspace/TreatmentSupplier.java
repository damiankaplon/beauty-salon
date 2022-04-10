package pl.damiankaplon.beautyspace;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.damiankaplon.beautyspace.core.domain.Treatment;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@NoArgsConstructor
@AllArgsConstructor
public class TreatmentSupplier {

    private Supplier<Treatment> supplier;

    private Faker faker = new Faker();

    public Treatment random() {
        return Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(faker.lorem().characters(20))
                .shortDescription(faker.lorem().characters(100))
                .fullDescription(faker.lorem().characters(255))
                .priceRange((float) faker.number().numberBetween(10, 99), (float) faker.number().numberBetween(100, 1000))
                .types(Set.of("Face"))
                .images(Set.of(faker.lorem().characters(10) + ".jpg"))
                .aproxTime(Duration.ofSeconds(7200))
                .build();
    }

    public Treatment randomWithUuid(UUID uuid) {
        return Treatment.builder()
                .uuid(uuid)
                .name(faker.lorem().characters(20))
                .shortDescription(faker.lorem().characters(100))
                .fullDescription(faker.lorem().characters(255))
                .priceRange((float) faker.number().numberBetween(10, 99), (float) faker.number().numberBetween(100, 1000))
                .types(Set.of("Face"))
                .images(Set.of(faker.lorem().characters(10) + "jpg"))
                .aproxTime(Duration.ofSeconds(7200))
                .build();
    }

    public Treatment randomWithName(String name) {
        return Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .shortDescription(faker.lorem().characters(100))
                .fullDescription(faker.lorem().characters(255))
                .priceRange((float) faker.number().numberBetween(10, 99), (float) faker.number().numberBetween(100, 1000))
                .types(Set.of("Face"))
                .images(Set.of(faker.lorem().characters(10) + "jpg"))
                .aproxTime(Duration.ofSeconds(7200))
                .build();
    }

    public Treatment randomWithNameAndType(String name, String type) {
        return Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .shortDescription(faker.lorem().characters(100))
                .fullDescription(faker.lorem().characters(255))
                .priceRange((float) faker.number().numberBetween(10, 99), (float) faker.number().numberBetween(100, 1000))
                .types(Set.of(type))
                .images(Set.of(faker.lorem().characters(10) + "jpg"))
                .aproxTime(Duration.ofSeconds(7200))
                .build();
    }

}
