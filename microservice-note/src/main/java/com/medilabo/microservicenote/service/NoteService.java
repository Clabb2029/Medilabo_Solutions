package com.medilabo.microservicenote.service;

import com.medilabo.microservicenote.exception.NoteNotFoundException;
import com.medilabo.microservicenote.model.Note;
import com.medilabo.microservicenote.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllPatientNotes(Integer patientId) {
        return noteRepository.findAllByPatientIdOrderByDateDesc(patientId);
    }

    public Note getNoteById(String id) {
        return noteRepository.findById(id).orElseThrow(null);
    }

    public Note createNote(Note note) {
        return noteRepository.insert(note);
    }

    public Note updateNote(String id, Note note) {
        Note existingNote = noteRepository.findById(id).orElse(null);
        if (existingNote != null) {
            existingNote.setDescription(note.getDescription());
            existingNote.setDate(note.getDate());
            return noteRepository.save(existingNote);
        } else {
            throw new NoteNotFoundException("Note avec l'ID " + id + " non trouvée");
        }
    }

}
