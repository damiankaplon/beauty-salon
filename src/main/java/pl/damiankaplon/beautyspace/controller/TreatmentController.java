package pl.damiankaplon.beautyspace.controller;

import lombok.RequiredArgsConstructor;
import org.eclipse.collections.impl.list.Interval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.controller.form.SearchForm;
import pl.damiankaplon.beautyspace.picture.PictureDto;
import pl.damiankaplon.beautyspace.picture.PictureService;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;
import pl.damiankaplon.beautyspace.treatment.domain.TreatmentService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/treatment")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;
    private final PictureService pictureService;

    @GetMapping("")
    public String getPagedTreatments(Model model, @RequestParam("page")Optional<Integer> page) {
        int currentPage = page.orElse(0);
        int pageSize = 6;
        Page<Treatment> treatmentsPage = treatmentService.getTreatmentsPage(PageRequest.of(currentPage, pageSize));
        model.addAttribute("dtoPage", treatmentsPage);
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("types", treatmentService.getAllTypes());
        model.addAttribute("pageNumbers", getNextFivePagesNumbers(currentPage, treatmentsPage.getTotalPages()));
        return "treatment";
    }

    private Interval getNextFivePagesNumbers(int currentPage, int totalPages) {
        if (totalPages <= 5) return Interval.fromTo(1, totalPages);
        if (currentPage + 2 >= totalPages) return Interval.fromTo(totalPages - 5, totalPages);
        else return Interval.fromTo(currentPage - 2, currentPage + 2);
    }

    @GetMapping("/uuid/{uuid}")
    public String getTreatmentDetails(@PathVariable String uuid, Model model) {
        UUID reqUuid = UUID.fromString(uuid);
        Treatment dto = treatmentService.getTreatment(reqUuid);
        model.addAttribute("dto", dto);
        return "treatment-details";
    }

    @PostMapping("/search")
    public String getTreatmentsByName(SearchForm form, Model model) {
        List<Treatment> dtos = treatmentService.getAllByNameAndType(form.getName(), form.getChosenType());
        model.addAttribute("dtos", dtos);
        return "treatments-container";
    }


    @GetMapping("/add")
    public String getAddTreatmentPage(Model model) {
        TreatmentForm form = new TreatmentForm();
        model.addAttribute("form", form);
        model.addAttribute("types", treatmentService.getAllTypes());
        return "add";
    }

    @PostMapping("/add")
    public String addNewTreatment(TreatmentForm form, @RequestParam("pic") MultipartFile[] pictures, Model model) throws IOException {
        List<PictureDto> picDto = pictureService.upload(List.of(pictures));
        Treatment toAdd = Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(form.getName())
                .shortDescription(form.getShortDescription())
                .fullDescription(form.getFullDescription())
                .types(form.getChosenTypes())
                .priceRange(form.getMinPriceValue(), form.getMaxPriceValue())
                .aproxTime(form.getAproxTimeAsLocalTime())
                .images(picDto.stream()
                        .map(PictureDto::getPathToFile)
                        .collect(Collectors.toSet()))
                .build();
        Treatment added = treatmentService.addNewTreatment(toAdd);
        model.addAttribute("treatment", added);
        return "common/success";
    }
}
