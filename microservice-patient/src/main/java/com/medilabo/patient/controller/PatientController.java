package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/getPatients")
    public ResponseEntity<Iterable<Patient>> getPatients() {
        log.info("Request received to fetch patient list");
        Iterable<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/getPatient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") String id) {
        log.info("Request received to fetch patient with ID: {}", id);
        try {
            Patient patient = patientService.getPatientById(Integer.valueOf(id));
            log.debug("Patient found: {}", patient);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            log.warn("Patient with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/createPatient")
    public ResponseEntity<?> createPatient(@Valid @RequestBody Patient patient, BindingResult bindingResult) {
        log.info("Request received to create new patient");
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            log.warn("Error in patient form: {}", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Patient createdPatient = patientService.createPatient(patient);
            log.debug("Patient created: {}", createdPatient);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
        } catch (Exception e) {
            log.warn("Error while creating patient: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updatePatient/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable("id") String id, @Valid @RequestBody Patient patient, BindingResult bindingResult) {
        log.info("Request received to update patient with ID: {}", id);
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            log.warn("Error in patient form: {}", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Patient updatedPatient = patientService.updatePatient(Integer.valueOf(id), patient);
            log.debug("Patient updated: {}", updatedPatient);
            return ResponseEntity.ok(updatedPatient);
        } catch (Exception e) {
            log.warn("Error while updating patient: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deletePatient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") String id) {
        log.info("Request received to delete patient with ID: {}", id);
        try {
            patientService.deletePatient(Integer.valueOf(id));
            log.debug("Patient with ID {} deleted", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.warn("Error while deleting patient: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
