package pl.damiankaplon.beautyspace.controller;

import lombok.RequiredArgsConstructor;
import org.eclipse.collections.impl.list.Interval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.Optional;

@Controller
@RequestMapping("/treatment")
@RequiredArgsConstructor
class TreatmentController {

    private final TreatmentService treatmentService;
    private final PictureService pictureService;

//    @GetMapping("")
//    String getListedServices(Model model) {
//        List<TreatmentDto> treatments = treatmentService.getAllTreatments();
//        model.addAttribute("treatments", treatments);
//        return "treatment";
//    }

    @GetMapping("")
    String getListedServicesByPage(Model model, @RequestParam("page")Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = 6;
        Page<TreatmentDto> dtoPage = treatmentService.geTreatmentsPage(PageRequest.of(currentPage, pageSize));
        model.addAttribute("dtoPage", dtoPage);

        int totalPages = dtoPage.getTotalPages();
        if (totalPages > 0) {
            model.addAttribute("pageNumbers", Interval.oneTo(totalPages));
        }

        return "treatment";
    }

    @GetMapping("/add")
    String getAddServicePage(Model model) {
        TreatmentForm form = new TreatmentForm();
        model.addAttribute("form", form);
        return "add";
    }

    @PostMapping("/add")
    String addNewTreatment(TreatmentForm form, @RequestParam("pic") MultipartFile picture, Model model) throws IOException {
        PictureDto picDto = pictureService.upload(picture);
        TreatmentDto added = treatmentService.addNewTreatment(form, picDto);
        model.addAttribute("treatment", added);
        return "common/success";
    }
}
