package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class PatientController {

    private final PatientService patientService;
    private final PagedResourcesAssembler<Patient> pagedResourcesAssembler;

    public PatientController(PatientService patientService, PagedResourcesAssembler<Patient> pagedResourcesAssembler) {
        this.patientService = patientService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(path="/patients")
    public ResponseEntity<PagedModel<Patient>> getPatients(@RequestParam(defaultValue = "0") Integer page) {
        log.info("Request received to fetch patient list");
        Page<Patient> patients = patientService.getAllPatients(PageRequest.of(page, 4));
        PagedModel<EntityModel<Patient>> entityModelPagedModel = pagedResourcesAssembler.toModel(patients);
        PagedModel<Patient> patientPagedModel = PagedModel.of(entityModelPagedModel.getContent().stream().map(EntityModel::getContent).collect(Collectors.toList()), entityModelPagedModel.getMetadata());
        return ResponseEntity.ok(patientPagedModel);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Integer id) {
        log.info("Request received to fetch patient with ID: {}", id);
        try {
            Patient patient = patientService.getPatientById(id);
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

    @PutMapping("/patient/{id}/update")
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

    @DeleteMapping("/patient/{id}/delete")
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
