package pl.damiankaplon.beautyspace.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damiankaplon.beautyspace.account.AccountDto;
import pl.damiankaplon.beautyspace.account.AccountService;
import pl.damiankaplon.beautyspace.controller.form.RegisterForm;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
class AccountController {

    private final AccountService accountService;
    private final ModelMapper mapper;

    @GetMapping("/login")
    String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    String getRegisterPage(Model model) {
        RegisterForm form = new RegisterForm();
        model.addAttribute("form", form);
        return "register";
    }

    @PostMapping("/register")
    String registerAccount(RegisterForm form) {
        AccountDto dto = mapper.map(form, AccountDto.class);
        accountService.register(dto);
        return "redirect:/account/login?registration_success";
    }
}
