package com.example.bankaccount.repository;

import com.example.bankaccount.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
    /**
     * Récupère une liste d'opération pour un numéro de compte et une date de début
     * @param number numéro de compte
     * @return  Compte associé au numéro
     */
    List<Operation> findByAccount_NumberAndDateAfterOrderByDateDesc(String number, LocalDateTime since);
}
