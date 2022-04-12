package pl.damiankaplon.beautyspace.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum TreatmentType {

    FULL_BODY("Body"), FACE("Face"),
    LEGS("Cosmetic");

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
