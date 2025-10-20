package com.example.bankaccount.model;

import com.example.bankaccount.exception.DepositLimitException;
import com.example.bankaccount.exception.OverdraftLimitException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe Compte Épargne, étend de {@link Account}
 */
@Entity
@DiscriminatorValue("SAVINGS")
@Getter
@Setter
public class SavingsAccount extends Account {
    private double depositLimit = 22950.0;

    /**
     * Fonction de retrait d'argent si le solde final n'est pas négatif
     * @param amount    montant du retrait
     */
    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount < 0) {
            throw new OverdraftLimitException("Savings account cannot be overdrawn");
        }
        setBalance(getBalance() - amount);
    }

    /**
     * Fonction de dépot d'argent si le plafond n'est pas atteint
     * @param amount    montant du dépot
     */
    @Override
    public void deposit(double amount) {
        if(getBalance() + amount > depositLimit) {
            throw new DepositLimitException(String.format("Deposit limit (%s) exceeded", depositLimit));
        }
        super.deposit(amount);
    }
}
