package pl.damiankaplon.beautyspace.core.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.core.domain.dtos.Form;
import pl.damiankaplon.beautyspace.core.domain.dtos.ImageDto;
import pl.damiankaplon.beautyspace.core.domain.ports.incoming.Web;
import pl.damiankaplon.beautyspace.core.domain.ports.outgoing.Database;
import pl.damiankaplon.beautyspace.core.domain.ports.outgoing.ImageUploader;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TreatmentService implements Web {

    private final Database databasePort;
    private final ImageUploader imageUploader;

    @Override
    public Treatment getTreatment(UUID uuid) {
        return databasePort.findByUuid(uuid);
    }

    @Override
    public List<String> getAllTypes() {
        return Arrays.stream(TreatmentType.values())
                .map(TreatmentType::getBodyPartName)
                .collect(Collectors.toList());
    }

    @Override
    public Treatment editTreatment(UUID toChange, Form changes, MultipartFile[] imgs) throws IOException {
        Treatment toBeChanged = databasePort.findByUuid(toChange);
        List<ImageDto> images = imageUploader.upload(List.of(imgs));
        Treatment withChanges = Treatment.builder()
                .uuid(toBeChanged.getUuid())
                .name(changes.getName())
                .aproxTime(Duration.ofSeconds(Long.parseLong(changes.getAproxTime())))
                .shortDescription(changes.getShortDescription())
                .fullDescription(changes.getFullDescription())
                .images(images.stream().map(ImageDto::getPathToFile).collect(Collectors.toSet()))
                .types(changes.getChosenTypes())
                .priceRange(changes.getMinPriceValue(), changes.getMaxPriceValue())
                .build();
        return databasePort.update(withChanges);
    }

    @Override
    public void deleteTreatment(UUID uuid) {
        databasePort.delete(uuid);
    }

    @Override
    public Page<Treatment> getTreatmentsPage(int page) {
        int pageSize = 6;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<Treatment> treatments = databasePort.findAll();
        List<Treatment> treatmentsPage = getTreatmentsForPage(pageable, treatments);
        return new PageImpl<>(
                treatmentsPage, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), treatments.size()
        );
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Treatment addNewTreatment(Form form, MultipartFile[] imgs) throws IOException {
        List<ImageDto> images  = imageUploader.upload(List.of(imgs));
        Treatment toAdd = Treatment.builder()
                .uuid(UUID.randomUUID())
                .name(form.getName())
                .shortDescription(form.getShortDescription())
                .fullDescription(form.getFullDescription())
                .types(form.getChosenTypes())
                .priceRange(form.getMinPriceValue(), form.getMaxPriceValue())
                .aproxTime(form.getAproxTimeAsDuration())
                .images(images.stream()
                                .map(ImageDto::getPathToFile)
                                .collect(Collectors.toSet())
                )
                .build();
       return databasePort.save(toAdd);
    }

    private List<Treatment> getTreatmentsForPage(Pageable pageable, List<Treatment> treatments) {
        int treatmentsCount = treatments.size();
        int pageSize = pageable.getPageSize();
        int currPage = pageable.getPageNumber();
        int startItem = currPage * pageSize;

        List<Treatment> treatmentsPage;

        if (treatmentsCount < startItem) {
            treatmentsPage = Collections.emptyList();
        }
        else {
            int endIndex = Math.min(startItem + pageSize, treatmentsCount);
            treatmentsPage = treatments.subList(startItem, endIndex);
        }

        return treatmentsPage;
    }

    @Override
    public List<Treatment> getAllByName(String name) {
        return databasePort.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Treatment> getAllByNameAndType(String name, String type) {
        if (!getAllTypes().contains(type)) return databasePort.findByNameContainingIgnoreCase(name);
        else return databasePort.findAllByNameContainingAndTypesContaining(name, type);
    }
}
