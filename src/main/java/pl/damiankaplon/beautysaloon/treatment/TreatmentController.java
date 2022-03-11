package pl.damiankaplon.beautysaloon.treatment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/treatment")
@RequiredArgsConstructor
class TreatmentController {

    private final TreatmentService treatmentService;

    @GetMapping("")
    String getListedServicesPage() {
//        model.addAllAttributes(List<Service> services);
        return "treatment";
    }

    @GetMapping("/add")
    String getAddServicePage(Model model) {
        TreatmentDto dto = new TreatmentDto();
        model.addAttribute("dto", dto);
        return "add";
    }

    @PostMapping("/add")
    String addTreatment(TreatmentDto dto, @RequestParam("pic") MultipartFile uploadPicture, Model model) throws IOException {
        Picture picture = Picture.upload(uploadPicture);

        Treatment treatment = Treatment.of(dto);
        treatment.setPicture(picture);

        treatmentService.save(treatment);

        return "common/success";
    }
}
