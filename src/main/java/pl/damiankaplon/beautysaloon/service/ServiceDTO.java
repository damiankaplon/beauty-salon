package pl.damiankaplon.beautysaloon.service;

import lombok.Data;

@Data
public class ServiceDTO {
    private String name;
    private PriceRange priceRange;
    private String shortDescription, FullDescription;

}
