package com.example.bankaccount.exception;

/**
 * Classe exception levée lorsqu'un dépôt dépasse le plafond autorisé
 * sur un compte épargne
 */
public class DepositLimitException extends RuntimeException {
    /**
     * Crée une nouvelle exception indiquant que le dépôt dépasse
     * le plafond autorisé.
     *
     * @param message description de l'erreur
     */
    public DepositLimitException(String message) {
        super(message);
    }
}
