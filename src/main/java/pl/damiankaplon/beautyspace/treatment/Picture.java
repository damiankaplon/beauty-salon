package pl.damiankaplon.beautyspace.treatment;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Picture {
    private String pathToFile;
}
