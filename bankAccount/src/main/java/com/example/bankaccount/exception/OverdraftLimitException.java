package com.example.bankaccount.exception;

/**
 * Classe exception levée lorsqu'un retrait dépasse e seuil
 * de découvert sur un compte bancaire
 */
public class OverdraftLimitException extends RuntimeException {
    /**
     * Crée une nouvelle exception indiquant que le retrait dépasse
     * le seuil de découvert.
     *
     * @param message description de l'erreur
     */
    public OverdraftLimitException(String message) {
        super(message);
    }
}
