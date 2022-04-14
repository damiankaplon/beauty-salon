package pl.damiankaplon.beautyspace.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
