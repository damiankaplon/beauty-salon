package pl.damiankaplon.beautyspace.account;

import org.springframework.data.jpa.repository.JpaRepository;

interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
}
