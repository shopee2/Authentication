package org.maimeemaineemaicode.register.repository;

import org.maimeemaineemaicode.register.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}