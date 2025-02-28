package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        log.info("Requête reçue pour récupérer la liste des patients");
        Page<Patient> patients = patientService.getAllPatients(PageRequest.of(page, 10));
        PagedModel<EntityModel<Patient>> entityModelPagedModel = pagedResourcesAssembler.toModel(patients);
        PagedModel<Patient> patientPagedModel = PagedModel.of(entityModelPagedModel.getContent().stream().map(EntityModel::getContent).collect(Collectors.toList()), entityModelPagedModel.getMetadata());
        return ResponseEntity.ok(patientPagedModel);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Integer id) {
        log.info("Requête reçue pour récupérer le patient avec l'ID : {}", id);
        try {
            Patient patient = patientService.getPatientById(id);
            log.debug("Patient trouvé : {}", patient);
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            log.warn("Patient avec l'ID {} non trouvé", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/createPatient")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        log.info("Requête reçue pour créer un nouveau patient");
        try {
            Patient createdPatient = patientService.createPatient(patient);
            log.debug("Patient créé : {}", createdPatient);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
        } catch (Exception e) {
            log.warn("Erreur lors de la création du patient : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/patient/{id}/update")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") Integer id, @RequestBody Patient patient) {
        log.info("Requête reçue pour mettre à jour le patient avec l'ID : {}", id);
        try {
            Patient updatedPatient = patientService.updatePatient(id, patient);
            log.debug("Patient mis à jour : {}", updatedPatient);
            return ResponseEntity.ok(updatedPatient);
        } catch (Exception e) {
            log.warn("Erreur lors de la mise à jour du patient : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/patient/{id}/delete")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Integer id) {
        log.info("Requête reçue pour supprimer le patient avec l'ID : {}", id);
        try {
            patientService.deletePatient(id);
            log.debug("Patient avec l'ID {} supprimé", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.warn("Erreur lors de la suppression du patient : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
