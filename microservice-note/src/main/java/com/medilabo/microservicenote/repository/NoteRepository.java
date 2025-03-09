package com.medilabo.microservicenote.repository;

import com.medilabo.microservicenote.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findAllByPatientIdOrderByDateDesc(Integer patientId);
}
