package pl.damiankaplon.beautyspace.controller;

import lombok.RequiredArgsConstructor;
import org.eclipse.collections.impl.list.Interval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.picture.PictureDto;
import pl.damiankaplon.beautyspace.picture.PictureService;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;
import pl.damiankaplon.beautyspace.treatment.Treatment;
import pl.damiankaplon.beautyspace.treatment.TreatmentService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/treatment")
@RequiredArgsConstructor
class TreatmentController {

    private final TreatmentService treatmentService;
    private final PictureService pictureService;

    @GetMapping("")
    public String getPagedTreatments(Model model, @RequestParam("page")Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int pageSize = 6;
        Page<Treatment> treatmentsPage = treatmentService.geTreatmentsPage(PageRequest.of(currentPage, pageSize));
        model.addAttribute("dtoPage", treatmentsPage);

        int totalPages = treatmentsPage.getTotalPages();
        if (totalPages > 0) {
            model.addAttribute("pageNumbers", Interval.oneTo(totalPages));
        }

        return "treatment";
    }

    @GetMapping("/{reqUuid}")
    public String getTreatment(@PathVariable String reqUuid, Model model) {
        UUID uuid = UUID.fromString(reqUuid);
        Treatment dto = treatmentService.getTreatment(uuid);
        model.addAttribute("dto", dto);

        return "treatment-details";
    }

    @GetMapping("/add")
    public String getAddTreatmentPage(Model model) {
        TreatmentForm form = new TreatmentForm();
        model.addAttribute("form", form);
        return "add";
    }

    @PostMapping("/add")
    public String addNewTreatment(TreatmentForm form, @RequestParam("pic") MultipartFile picture, Model model) throws IOException {
        PictureDto picDto = pictureService.upload(picture);
        Treatment added = treatmentService.addNewTreatment(form, picDto);
        model.addAttribute("treatment", added);
        return "common/success";
    }
}
