package pl.damiankaplon.beautyspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/index.html", ""})
public class BasicController {

    @GetMapping
    String getIndexPage() {
        return "index";
    }
}
