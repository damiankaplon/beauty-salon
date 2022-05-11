package pl.damiankaplon.beautyspace.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accRepo;
    private final PasswordEncoder passEncoder;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesnt exists"));
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Account acc = accRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with given email doesnt exists"));
        acc.setResetPasswordToken(token);
        accRepo.save(acc);
    }

    public UserDetails getByResetPasswordToken(String token) {
        return accRepo.findByResetPasswordToken(token)
                .orElseThrow(() -> new UsernameNotFoundException("No such reset token"));
    }

    public void updatePassword(UserDetails userDetails, String newPassword) {
        String encodedPassword = passEncoder.encode(newPassword);

        Account acc = accRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with given username doesnt exists"));
        acc.setPassword(encodedPassword);

        acc.setResetPasswordToken(null);
        accRepo.save(acc);
    }

    public AccountDto register(AccountDto dto) {
        Account account = Account.builder()
                .uuid(UUID.randomUUID())
                .email(dto.getEmail())
                .username(dto.getEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .password(passEncoder.encode(dto.getPassword()))
                .authority(AccountAuthority.READ_AUTHORITY)
                .enabled(true)
                .expired(false)
                .locked(false)
                .build();
        return mapper.map(accRepo.save(account), AccountDto.class);
    }
}
