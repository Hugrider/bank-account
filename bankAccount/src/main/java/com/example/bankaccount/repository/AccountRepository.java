package com.example.bankaccount.repository;

import com.example.bankaccount.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Récupère un compte via son numéro
     * @param number numéro de compte
     * @return  Compte associé au numéro
     */
    Optional<Account> findByNumber(String number);
}
