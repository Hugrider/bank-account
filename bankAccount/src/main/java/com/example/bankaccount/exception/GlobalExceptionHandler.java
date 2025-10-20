package com.example.bankaccount.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe de gestion globale des exceptions
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception solde sous le seuil authorisé
     * @param ex Exception
     * @return   Réponse https bad request contenant le message d'erreur
     */
    @ExceptionHandler(OverdraftLimitException.class)
    public ResponseEntity<String> handleOverdraftLimit(OverdraftLimitException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Exception dépot dépasse le plafond authorisé
     * @param ex Exception
     * @return   Réponse https bad request contenant le message d'erreur
     */
    @ExceptionHandler(DepositLimitException.class)
    public ResponseEntity<String> handleSavingsAccountOverdrawn(DepositLimitException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
