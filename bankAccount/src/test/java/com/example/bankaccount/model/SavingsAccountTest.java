package com.example.bankaccount.model;

import com.example.bankaccount.exception.OverdraftLimitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test de la classe {@link SavingsAccount}
 */
class SavingsAccountTest {

    /**
     * Test de la méthode de retrait d'argent
     */
    @Test
    void testWithdraw() {
        SavingsAccount account = new SavingsAccount();
        account.setBalance(200.0);
        account.withdraw(75.0);
        Assertions.assertEquals(125.0, account.getBalance());
    }

    /**
     * Test de la méthode de retrait d'argent en dépassant le solde disponible
     */
    @Test
    void testWithdraw_OverdraftLimitExceeded() {
        SavingsAccount account = new SavingsAccount();
        account.setBalance(100.0);

        Assertions.assertThrows(
            OverdraftLimitException.class,
            () -> account.withdraw(150.0)
        );
    }

    /**
     * Test de la méthode de dépôt d'argent
     */
    @Test
    void testDeposit_DepositLimitExceeded() {
        SavingsAccount account = new SavingsAccount();
        account.setBalance(22000.0);

        Assertions.assertThrows(
            com.example.bankaccount.exception.DepositLimitException.class,
            () -> account.deposit(1000.0)
        );
    }
}
