package com.example.bankaccount.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe abstraite Compte
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private Double balance;

    public  Account() {
        this.balance = 0.0;
    }

    /**
     * Fonction de dépot d'argent
     * @param amount    montant du dépot
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Fonction abstraite de retrait d'argent
     * @param amount    montant du retrait
     */
    public abstract void withdraw(double amount);
}