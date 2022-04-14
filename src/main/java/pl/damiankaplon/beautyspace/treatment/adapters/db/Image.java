package pl.damiankaplon.beautyspace.treatment.adapters.db;

import lombok.Data;

import javax.persistence.*;

@Embeddable
@Data
class Image {
    private String src;
}
