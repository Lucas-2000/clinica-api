package br.com.dev.clinica.infra.exceptions;

public class OverlappingDateException extends RuntimeException {

    public OverlappingDateException(String message) {
        super(message);
    }
}
