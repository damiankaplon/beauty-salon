package pl.damiankaplon.beautyspace.treatment;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;

import java.time.LocalTime;

class TreatmentMapper extends ModelMapper {
    public TreatmentMapper() {
        super();
        PropertyMap<Treatment, TreatmentDto> picturePathMap = new PropertyMap<>() {
            public void configure() {
                map().setPicturePath(source.getPicture().getPathToFile());
                map().setMinPrice(source.getPriceRange().getMinPrice());
                map().setMaxPrice(source.getPriceRange().getMaxPrice());
                map().setAproxTime(source.getAproxTime());
            }
        };
        PropertyMap<TreatmentForm, TreatmentDto> aproxTimeMap = new PropertyMap<>() {
            public void configure() {
                map().setAproxTime(source.getAproxTimeAsLocalTime());
            }
        };
        this.addMappings(picturePathMap);
        this.addMappings(aproxTimeMap);
    }
}
