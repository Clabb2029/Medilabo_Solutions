package com.medilabo.patient.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
