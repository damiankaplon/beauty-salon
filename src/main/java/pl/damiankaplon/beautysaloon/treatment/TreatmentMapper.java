package pl.damiankaplon.beautysaloon.treatment;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

class TreatmentMapper extends ModelMapper {
    public TreatmentMapper() {
        super();
        PropertyMap<Treatment, TreatmentDto> picturePathMap = new PropertyMap<>() {
            public void configure() {
                map().setPicturePath(source.getPicture().getPathToFile());
                map().setMinPrice(source.getPriceRange().getMinPrice());
                map().setMaxPrice(source.getPriceRange().getMaxPrice());
            }
        };
        this.addMappings(picturePathMap);
    }
}
