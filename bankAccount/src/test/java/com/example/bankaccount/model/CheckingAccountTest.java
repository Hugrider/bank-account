package com.example.bankaccount.model;

import com.example.bankaccount.exception.OverdraftLimitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test de la classe {@link CheckingAccount}
 */
class CheckingAccountTest {

    /**
     * Test de la méthode de retrait d'argent dans la limite du découvert autorisé
     */
    @Test
    void testWithdraw() {
        CheckingAccount acc = new CheckingAccount();
        acc.setBalance(100.0);
        acc.setOverdraftLimit(50.0);

        acc.withdraw(120.0);

        Assertions.assertEquals(-20.0, acc.getBalance());
    }

    /**
     * Test de la méthode de retrait d'argent en dépassant le découvert autorisé
     */
    @Test
    void testWithdraw_OverdraftLimitExceeded() {
        CheckingAccount acc = new CheckingAccount();
        acc.setBalance(100.0);
        acc.setOverdraftLimit(50.0);

        Assertions.assertThrows(OverdraftLimitException.class, () -> acc.withdraw(200.0));
    }
}
