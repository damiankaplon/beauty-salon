package pl.damiankaplon.beautyspace.account;

import org.springframework.security.core.GrantedAuthority;

enum AccountAuthority implements GrantedAuthority {
    READ_AUTHORITY, WRITE_PRIVILEGE;

    @Override
    public String getAuthority() {
        return name();
    }
}
