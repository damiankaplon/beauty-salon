package pl.damiankaplon.beautyspace.treatment.adapters.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum TreatmentType {

    BODY("Body"), FACE("Face"),
    COSMETICS("Cosmetic");


    final String bodyPartName;

    public static TreatmentType fromString(String text) {
        for (TreatmentType type : TreatmentType.values()) {
            if (type.bodyPartName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
