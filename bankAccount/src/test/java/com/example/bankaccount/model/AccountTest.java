package com.example.bankaccount.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test de la classe {@link Account}
 */
class AccountTest {

    /**
     * Test de la méthode de dépôt d'argent
     */
    @Test
    void testDeposit() {
        Account account = new CheckingAccount();
        account.setBalance(100.0);
        account.deposit(50.0);
        Assertions.assertEquals(150.0, account.getBalance());
    }
}
