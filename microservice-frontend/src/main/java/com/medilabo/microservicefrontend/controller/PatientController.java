package com.medilabo.microservicefrontend.controller;

import com.medilabo.microservicefrontend.bean.PatientBean;
import com.medilabo.microservicefrontend.proxy.MicroservicePatientProxy;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class PatientController {

    private final MicroservicePatientProxy patientProxy;

    public PatientController(MicroservicePatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        log.info("Requête reçue pour afficher la page de connexion");
        model.addAttribute("pageTitle", "Connexion");
        return "login";
    }

    @GetMapping("/patient-list")
    public String showPatientList(Model model, @RequestParam(defaultValue = "0") Integer page) {
        log.info("Requête reçue pour afficher la liste des patients");
        PagedModel<PatientBean> patients = patientProxy.getPatients(page);
        model.addAttribute("patients", patients);
        model.addAttribute("pageTitle", "Liste de vos patients");
        model.addAttribute("currentPage", page);
        return "patientList";
    }

    @GetMapping("/create-patient")
    public String showCreatePatientForm(Model model) {
        log.info("Requête reçue pour afficher la page de création d'un patient");
        model.addAttribute("patient", new PatientBean());
        model.addAttribute("pageTitle", "Création d'un nouveau patient");
        return "patientEdition";
    }

    @GetMapping("/patient/{id}")
    public String showUpdatePatientForm(@PathVariable Integer id, Model model) {
        log.info("Requête reçue pour afficher la page de modification d'un patient");
        try {
            PatientBean patient = patientProxy.getPatientById(id);
            log.debug("Patient récupéré avec succès");
            model.addAttribute("patient", patient);
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.warn("Erreur lors de la récupération des informations du patient : {}", e.getMessage());
            model.addAttribute("patient", new PatientBean());
        }
        model.addAttribute("pageTitle", "Modification de patient");
        return "patientEdition";
    }

    @PostMapping("/create-patient")
    public String createPatient(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult bindingResult) {
        log.info("Requête reçue pour créer un patient");
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            log.warn("Erreur(s) dans le formulaire de création du patient : {}", errors);
            return "patientEdition";
        }
        patientProxy.createPatient(patient);
        log.debug("Patient créé avec succès");
        return "redirect:http://localhost:8080/microservice-frontend/patient-list?toastMessage=Le patient a bien été créé";
    }

    @PostMapping("/patient/{id}/update")
    public String updatePatient(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult bindingResult) {
        log.info("Requête reçue pour modifier un patient");
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            log.warn("Erreur(s) dans le formulaire de modification du patient : {}", errors);
            return "patientEdition";
        }
        patientProxy.updatePatient(patient.getId(), patient);
        log.debug("Patient modifié avec succès");
        return "redirect:http://localhost:8080/microservice-frontend/patient-list?toastMessage=Les informations du patient ont bien été mises à jour";
    }

    @GetMapping("/patient/{id}/delete")
    public String deletePatient(@PathVariable Integer id) {
        log.info("Requête reçue pour supprimer un patient");
        patientProxy.deletePatientById(id);
        log.debug("Patient supprimé avec succès");
        return "redirect:http://localhost:8080/microservice-frontend/patient-list?toastMessage=Le patient a bien été supprimé";
    }

}
