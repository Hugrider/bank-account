package com.example.bankaccount.model;

import com.example.bankaccount.exception.OverdraftLimitException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe Compte Courant, étend de {@link Account}
 */
@Entity
@DiscriminatorValue("CHECKING")
@Getter
@Setter
public class CheckingAccount extends Account {
    private double overdraftLimit = 500.0;

    /**
     * Fonction de retrait d'argent si le solde final n'est inférieur au découvert authorisé
     * @param amount    montant du retrait
     */
    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount < -overdraftLimit) {
            throw new OverdraftLimitException(String.format("Overdraft limit (%s) exceeded", overdraftLimit));
        }
        setBalance(getBalance() - amount);
    }
}
