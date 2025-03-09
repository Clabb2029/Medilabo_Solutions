package com.medilabo.microservicefrontend.controller;

import com.medilabo.microservicefrontend.bean.NoteBean;
import com.medilabo.microservicefrontend.bean.PatientBean;
import com.medilabo.microservicefrontend.bean.ReportBean;
import com.medilabo.microservicefrontend.proxy.MicroserviceDiabetesRiskProxy;
import com.medilabo.microservicefrontend.proxy.MicroserviceNoteProxy;
import com.medilabo.microservicefrontend.proxy.MicroservicePatientProxy;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class PatientController {

    private final MicroservicePatientProxy patientProxy;
    private final MicroserviceNoteProxy noteProxy;
    private final MicroserviceDiabetesRiskProxy riskProxy;

    public PatientController(MicroservicePatientProxy patientProxy, MicroserviceNoteProxy noteProxy, MicroserviceDiabetesRiskProxy riskProxy) {
        this.patientProxy = patientProxy;
        this.noteProxy = noteProxy;
        this.riskProxy = riskProxy;
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
    public String showPatientDetails(@PathVariable Integer id, Model model) {
        log.info("Requête reçue pour afficher la page de détails d'un patient");
        try {
            PatientBean patient = patientProxy.getPatientById(id);
            List<NoteBean> notes = noteProxy.getPatientNotes(id);
            ReportBean report = riskProxy.getPatientDiabetesReportById(id);
            log.debug("Patient récupéré avec succès");
            model.addAttribute("patient", patient);
            model.addAttribute("notes", notes);
            model.addAttribute("report", report);
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.warn("Erreur lors de la récupération des informations du patient : {}", e.getMessage());
            model.addAttribute("patient", new PatientBean());
            model.addAttribute("notes", new ArrayList<>());
            model.addAttribute("report", new ReportBean());
        }
        model.addAttribute("pageTitle", "Fiche patient");
        return "patientDetails";
    }

    @GetMapping("/patient/{id}/edition")
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
        return "redirect:http://localhost:8080/microservice-frontend/patient/" + patient.getId() + "?toastMessage=" + URLEncoder.encode("Le patient a bien été créé", StandardCharsets.UTF_8);
    }

    @PostMapping("/patient/{id}/update")
    public String updatePatient(@PathVariable Integer id, @Valid @ModelAttribute("patient") PatientBean patient, BindingResult bindingResult) {
        log.info("Requête reçue pour modifier un patient");
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            log.warn("Erreur(s) dans le formulaire de modification du patient : {}", errors);
            return "patientEdition";
        }
        patientProxy.updatePatient(id, patient);
        log.debug("Patient modifié avec succès");
        return "redirect:http://localhost:8080/microservice-frontend/patient/" + patient.getId() + "?toastMessage=" + URLEncoder.encode("Les informations du patient ont bien été mises à jour", StandardCharsets.UTF_8);
    }

    @GetMapping("/patient/{id}/delete")
    public String deletePatient(@PathVariable Integer id) {
        log.info("Requête reçue pour supprimer un patient");
        patientProxy.deletePatientById(id);
        log.debug("Patient supprimé avec succès");
        return "redirect:http://localhost:8080/microservice-frontend/patient-list?toastMessage=" + URLEncoder.encode("Le patient a bien été supprimé", StandardCharsets.UTF_8);
    }

}
