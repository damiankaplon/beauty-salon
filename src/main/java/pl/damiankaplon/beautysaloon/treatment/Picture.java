package pl.damiankaplon.beautysaloon.treatment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
class Picture {
    private String pathToFile;
}
