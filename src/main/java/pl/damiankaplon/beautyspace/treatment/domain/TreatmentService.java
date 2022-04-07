package pl.damiankaplon.beautyspace.treatment.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.damiankaplon.beautyspace.treatment.domain.ports.incoming.IncomingPort;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.Database;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TreatmentService implements IncomingPort {

    private final Database databasePort;

    @Override
    public Treatment addNewTreatment(Treatment treatment) {
        return databasePort.save(treatment);
    }

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
    public Treatment editTreatment(Treatment changes, UUID toChange) {
        Treatment toBeChanged = databasePort.findByUuid(toChange);
        Treatment withChanges = Treatment.builder()
                .uuid(toBeChanged.getUuid())
                .name(changes.getName())
                .aproxTime(changes.getAproxTime())
                .shortDescription(changes.getShortDescription())
                .fullDescription(changes.getFullDescription())
                .images(new HashSet<>(changes.getImagesSrcs()))
                .types(new HashSet<>(changes.getTypesNames()))
                .priceRange(changes.getMinPrice(), changes.getMaxPrice())
                .build();
        return databasePort.update(withChanges);
    }

    @Override
    public Page<Treatment> getTreatmentsPage(Pageable pageable) {
        List<Treatment> treatments = databasePort.findAll();
        List<Treatment> treatmentsPage = getTreatmentsForPage(pageable, treatments);
        return new PageImpl<>(
                treatmentsPage, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), treatments.size()
        );
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
        return databasePort.findAllByNameContaining(name);
    }


    @Override
    public List<Treatment> getAllByType(String type) {
        return databasePort.findAllByTypesContains(type);
    }

    @Override
    public List<Treatment> getAllByNameAndType(String name, String type) {
        return databasePort.findAllByNameContainingAndTypesContaining(name, type);
    }
}
