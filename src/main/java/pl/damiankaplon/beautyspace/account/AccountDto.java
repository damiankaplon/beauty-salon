package pl.damiankaplon.beautyspace.account;

import lombok.Data;

import java.util.UUID;

@Data
public class AccountDto {
    private String name, surname, email, password;
    private UUID uuid;
}
