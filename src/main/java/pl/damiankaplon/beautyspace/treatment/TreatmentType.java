package pl.damiankaplon.beautyspace.treatment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TreatmentType {

    FULL_BODY("Full body"), HANDS("Hands"), FACE("Face"),
    LEGS("Legs"), BACK("Back"), CHEST("Chest"), STOMACH("Stomach");

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
