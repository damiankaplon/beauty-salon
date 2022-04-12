package pl.damiankaplon.beautyspace.account;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository repo;
    private final PasswordEncoder passEncoder;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User doesnt exists");
        else
            return user;
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
        return mapper.map(repo.save(account), AccountDto.class);
    }
}
