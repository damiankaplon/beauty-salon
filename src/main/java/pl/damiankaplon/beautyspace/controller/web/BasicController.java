package pl.damiankaplon.beautyspace.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/index.html","/home"})
public class BasicController {

    @GetMapping
    String getIndexPage() {
        return "index";
    }

    @GetMapping("/contact")
    String getContactPage() {
        return "contact";
    }
}
