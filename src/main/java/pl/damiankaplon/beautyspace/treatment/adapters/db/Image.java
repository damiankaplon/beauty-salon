package pl.damiankaplon.beautyspace.treatment.adapters.db;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String src;
    @ManyToOne
    private TreatmentEntity treatment;
}
