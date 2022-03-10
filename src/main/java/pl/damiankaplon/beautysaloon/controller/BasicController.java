package pl.damiankaplon.beautysaloon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class BasicController {

    @GetMapping
    String getIndexPage() {
        return "index";
    }

    @GetMapping("/offer")
    String getAlbumPage(Model model) {
//        model.addAllAttributes(List<Service> services);
        return "offer";
    }

    @GetMapping("/addOffer")
    String getAddOfferPage() {
        return "addOffer";
    }
}
