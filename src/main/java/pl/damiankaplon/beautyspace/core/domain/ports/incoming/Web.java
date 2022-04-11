package pl.damiankaplon.beautyspace.core.domain.ports.incoming;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.core.domain.Treatment;
import pl.damiankaplon.beautyspace.core.domain.dtos.Form;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface Web {
    Page<Treatment> getTreatmentsPage(int page);
    Treatment addNewTreatment(Form form, MultipartFile[] images) throws IOException;
    List<Treatment> getAllByName(String name);
    Treatment getTreatment(UUID uuid);
    List<Treatment> getAllByNameAndType(String name, String type);
    List<String> getAllTypes();
    Treatment editTreatment(UUID toChange, Form changes, MultipartFile[] imgs) throws IOException;
    void deleteTreatment(UUID fromString);
}
