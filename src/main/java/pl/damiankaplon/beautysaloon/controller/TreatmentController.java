package pl.damiankaplon.beautysaloon.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautysaloon.Picture.PictureDto;
import pl.damiankaplon.beautysaloon.Picture.PictureService;
import pl.damiankaplon.beautysaloon.controller.form.TreatmentForm;
import pl.damiankaplon.beautysaloon.treatment.TreatmentService;

import java.io.IOException;

@Controller
@RequestMapping("/treatment")
@RequiredArgsConstructor
class TreatmentController {

    private final TreatmentService treatmentService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    String getListedServicesPage() {
//        model.addAllAttributes(List<Service> services);
        return "treatment";
    }

    @GetMapping("/add")
    String getAddServicePage(Model model) {
        TreatmentForm form = new TreatmentForm();
        model.addAttribute("form", form);
        return "add";
    }

    @PostMapping("/add")
    String addNewTreatment(TreatmentForm form, @RequestParam("pic") MultipartFile picture) throws IOException {

        PictureDto picDto = pictureService.upload(picture);

        treatmentService.addNewTreatment(form, picDto);

        return "common/success";
    }
}
