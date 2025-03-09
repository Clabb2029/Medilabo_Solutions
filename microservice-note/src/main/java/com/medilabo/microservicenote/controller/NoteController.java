package com.medilabo.microservicenote.controller;

import com.medilabo.microservicenote.model.Note;
import com.medilabo.microservicenote.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/patientNotes")
    public ResponseEntity<List<Note>> getAllPatientNotes(Integer patientId) {
        log.info("Requête reçue pour récupérer la liste des notes du patient avec l'ID : {}", patientId);
        List<Note> notes = noteService.getAllPatientNotes(patientId);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String id) {
        log.info("Requête reçue pour récupérer la note avec l'ID : {}", id);
        try {
            Note note = noteService.getNoteById(id);
            log.debug("Note trouvée : {}", note);
            return ResponseEntity.ok(note);
        } catch (Exception e) {
            log.warn("Note avec l'ID {} non trouvée", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/createPatientNote")
    public ResponseEntity<Note> createPatientNote(@RequestBody Note note) {
        log.info("Requête reçue pour créer une nouvelle note pour le patient avec l'ID : {}", note.getPatientId());
        try {
            Note createdNote = noteService.createNote(note);
            log.debug("Note créée : {}", createdNote);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
        } catch (Exception e) {
            log.warn("Erreur lors de la création de la note : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/note/{id}/update")
    public ResponseEntity<Note> updatePatientNote(@PathVariable("id") String id, @RequestBody Note note) {
        log.info("Requête reçue pour mettre à jour la note avec l'ID : {}", id);
        try {
            Note updatedNote = noteService.updateNote(id, note);
            log.debug("Note mise à jour : {}", updatedNote);
            return ResponseEntity.ok(updatedNote);
        } catch (Exception e) {
            log.warn("Erreur lors de la modification de la note : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
