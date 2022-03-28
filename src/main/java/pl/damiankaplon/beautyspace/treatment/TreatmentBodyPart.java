package pl.damiankaplon.beautyspace.treatment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TreatmentBodyPart {

    FULL_BODY("FULL_BODY"), HANDS("HANDS"), FACE("FACE"), LEGS("LEGS"), BACK("BACK"), CHEST("CHEST"), STOMACH("STOMACH");

    final String bodyPartName;
}
