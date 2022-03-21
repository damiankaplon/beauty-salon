package pl.damiankaplon.beautyspace.treatment;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Picture {
    private String pathToFile;
}
