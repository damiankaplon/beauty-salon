package pl.damiankaplon.beautyspace.account;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private UUID uuid;
    private String username, email, name, surname, password;
    @Enumerated(EnumType.STRING)
    private AccountAuthority authority;
    private boolean expired, locked, enabled;

    private int getId() {
        return this.id;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    static AccountBuilder builder() {
        return new AccountBuilder();
    }

    static class AccountBuilder {
        private int id;
        private UUID uuid;
        private String username, email, name, surname, password;
        private AccountAuthority authority;
        private boolean expired, locked, enabled;

        private AccountBuilder() {}

        AccountBuilder id(int id) {
            this.id = id;
            return this;
        }
        AccountBuilder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        AccountBuilder username(String email) {
            this.username = email;
            return this;
        }

        AccountBuilder email(String email) {
            this.email = email;
            return this;
        }

        AccountBuilder name(String name) {
            this.name = name;
            return this;
        }

        AccountBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        AccountBuilder password(String password) {
            this.password = password;
            return this;
        }

        AccountBuilder authority(AccountAuthority authority) {
            this.authority = authority;
            return this;
        }

        AccountBuilder expired(boolean expired) {
            this.expired = expired;
            return this;
        }

        AccountBuilder locked(boolean locked) {
            this.locked = locked;
            return this;
        }

        AccountBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        Account build() {
            return new Account(
                    this.id, this.uuid, this.username, this.email, this.name, this.surname, this.password, this.authority, this.expired,
                    this.locked, this.enabled
            );
        }


    }
}
