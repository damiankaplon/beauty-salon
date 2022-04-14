package pl.damiankaplon.beautyspace.account;

import lombok.Data;

import java.util.UUID;

@Data
public class AccountDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private UUID uuid;
}
