package pl.damiankaplon.beautyspace.controller.web.form;

import lombok.Data;

@Data
public class RegisterForm {
    private String name, surname, email, password;
}
