package pl.damiankaplon.beautyspace.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.picture.PictureDto;
import pl.damiankaplon.beautyspace.picture.PictureService;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;
import pl.damiankaplon.beautyspace.treatment.TreatmentDto;
import pl.damiankaplon.beautyspace.treatment.TreatmentService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/treatment")
@RequiredArgsConstructor
class TreatmentController {

    private final TreatmentService treatmentService;
    private final PictureService pictureService;
    private final ModelMapper mapper;

    @GetMapping("")
    String getListedServicesPage(Model model) {
        List<TreatmentDto> treatments = treatmentService.getAllTreatments();
        model.addAttribute("treatments", treatments);
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
