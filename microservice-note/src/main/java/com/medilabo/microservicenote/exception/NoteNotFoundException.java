package com.medilabo.microservicenote.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
