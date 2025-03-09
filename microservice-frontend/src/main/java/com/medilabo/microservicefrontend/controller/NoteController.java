package com.medilabo.microservicefrontend.controller;

import com.medilabo.microservicefrontend.bean.NoteBean;
import com.medilabo.microservicefrontend.proxy.MicroserviceNoteProxy;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class NoteController {

    private final MicroserviceNoteProxy noteProxy;

    public NoteController(MicroserviceNoteProxy noteProxy) {
        this.noteProxy = noteProxy;
    }

    @GetMapping("/patient/{patientId}/create-note")
    public String showCreateNoteForm(@PathVariable("patientId") Integer patientId, Model model) {
        log.info("Requête reçue pour afficher la page de création d'une note pour un patient");
        NoteBean note = new NoteBean();
        note.setPatientId(patientId);
        model.addAttribute("note", note);
        model.addAttribute("pageTitle", "Création d'une nouvelle note d'observation");
        return "noteEdition";
    }

    @GetMapping("/patient/{patientId}/note/{noteId}/edition")
    public String showUpdatePatientNoteForm(@PathVariable String noteId, Model model) {
        log.info("Requête reçue pour afficher la page de modification d'une note patient");
        try {
            NoteBean note = noteProxy.getNoteById(noteId);
            log.debug("Note récupérée avec succès");
            model.addAttribute("note", note);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.warn("Erreur lors de la récupération de la note : {}", e.getMessage());
            model.addAttribute("note", new NoteBean());
        }
        model.addAttribute("pageTitle", "Modification de note d'observation");
        return "noteEdition";
    }

    @PostMapping("/patient/{patientId}/create-note")
    public String createNote(@PathVariable Integer patientId, @Valid @ModelAttribute("note") NoteBean note, BindingResult bindingResult) {
        log.info("Requête reçue pour créer une note");
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            log.warn("Erreur(s) dans le formulaire de création de la note : {}", errors);
            return "noteEdition";
        }
        note.setPatientId(patientId);
        noteProxy.createPatientNote(note);
        log.debug("Note créée avec succès");
        return "redirect:http://localhost:8080/microservice-frontend/patient/" + note.getPatientId() + "?toastMessage=" + URLEncoder.encode("La note a bien été créée", StandardCharsets.UTF_8);
    }

    @PostMapping("/patient/{patientId}/note/{noteId}/update")
    public String updateNote(@PathVariable String noteId, @Valid @ModelAttribute("note") NoteBean note, BindingResult bindingResult) {
        log.info("Requête reçue pour modifier une note");
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            log.warn("Erreur(s) dans le formulaire de modification de la note : {}", errors);
            return "noteEdition";
        }
        noteProxy.updatePatientNote(noteId, note);
        log.debug("Patient modifié avec succès");
        return "redirect:http://localhost:8080/microservice-frontend/patient/" + note.getPatientId() + "?toastMessage=" + URLEncoder.encode("La note a bien été mise à jour", StandardCharsets.UTF_8);
    }

}
