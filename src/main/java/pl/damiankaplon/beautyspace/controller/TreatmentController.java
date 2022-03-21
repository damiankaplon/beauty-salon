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
import java.util.List;
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
        int currentPage = page.orElse(0);
        int pageSize = 6;
        Page<Treatment> treatmentsPage = treatmentService.geTreatmentsPage(PageRequest.of(currentPage, pageSize));
        model.addAttribute("dtoPage", treatmentsPage);

        model.addAttribute("pageNumbers", getNextFivePagesNumbers(currentPage, treatmentsPage.getTotalPages()));

        return "treatment";
    }

    private Interval getNextFivePagesNumbers(int currentPage, int totalPages) {
        if (totalPages <= 5) return Interval.fromTo(1, totalPages);
        if (currentPage + 2 >= totalPages) return Interval.fromTo(totalPages - 5, totalPages);
        else return Interval.fromTo(currentPage - 2, currentPage + 2);
    }

    @GetMapping("/{uuidOrName}")
    public String getTreatment(@PathVariable String uuidOrName, Model model) {
        return responseBaseOnAttribute(uuidOrName, model);
    }

    private String responseBaseOnAttribute(String reqUuidOrName, Model model) {
        try {
            UUID uuid = UUID.fromString(reqUuidOrName);
            Treatment dto = treatmentService.getTreatment(uuid);
            model.addAttribute("dto", dto);
            return "treatment-details";

        } catch (IllegalArgumentException ex) {
            List<Treatment> dtos = treatmentService.getAllByName(reqUuidOrName);
            model.addAttribute("dtos", dtos);
            return "treatments-by-name";
        }
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
