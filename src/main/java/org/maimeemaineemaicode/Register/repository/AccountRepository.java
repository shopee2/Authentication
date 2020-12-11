package org.maimeemaineemaicode.register.repository;

import org.maimeemaineemaicode.register.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByUsername(String username);
}