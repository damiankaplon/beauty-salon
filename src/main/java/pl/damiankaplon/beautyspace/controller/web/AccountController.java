package pl.damiankaplon.beautyspace.controller.web;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damiankaplon.beautyspace.account.AccountDto;
import pl.damiankaplon.beautyspace.account.AccountService;
import pl.damiankaplon.beautyspace.controller.web.form.RegisterForm;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper mapper;
    private final JavaMailSender mailSender;

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
    String registerAccount(RegisterForm form, Model model) {
        AccountDto dto = mapper.map(form, AccountDto.class);
        if(!isPasswordValid(dto.getPassword()))
            return "redirect:/account/register?error_pass_policy";

        accountService.register(dto);
        return "redirect:/account/login?registration_success";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            accountService.updateResetPasswordToken(token, email);
            String resetPasswordLink = request.getRequestURL().toString().replace(request.getServletPath(), "")
                    + "/account/reset-password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset link.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "login";
    }
    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        message.setSubject(subject);

        message.setText(content);

        mailSender.send(message);
    }


    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        UserDetails userDetails = accountService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        if(!isPasswordValid(password)) {
            return "redirect:/account/reset-password?token=" + token + "&error_pass_policy";
        }


        UserDetails userDetails = accountService.getByResetPasswordToken(token);
        accountService.updatePassword(userDetails, password);

        model.addAttribute("message", "You have successfully changed your password.");

        return "login";
    }

    private boolean isPasswordValid(String password) {
        boolean isCapital = false;
        boolean isDigit = false;
        int minLong = 8;
        boolean isLong = false;
        boolean isSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            isCapital = isCapital || Character.isUpperCase(password.charAt(i));
        }
        for (int i = 0; i < password.length(); i++) {
            isDigit = isDigit || Character.isDigit(password.charAt(i));
        }
        for (int i = 0; i < password.length(); i++) {
            isDigit = isDigit || Character.isDigit(password.charAt(i));
        }
        for (int i = 0; i < password.length(); i++) {
            isSpecial = isSpecial || (!Character.isLetter(password.charAt(i)) && !Character.isDigit(password.charAt(i)));
        }
        isLong = password.length() > minLong ? true : false;
        return isDigit && isLong && isSpecial && isCapital;
    }
}
