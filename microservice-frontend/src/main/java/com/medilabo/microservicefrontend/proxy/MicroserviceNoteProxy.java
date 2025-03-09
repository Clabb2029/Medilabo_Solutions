package com.medilabo.microservicefrontend.proxy;

import com.medilabo.microservicefrontend.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-note", url = "http://localhost:8083")
public interface MicroserviceNoteProxy {

    @GetMapping(value = "/patientNotes")
    List<NoteBean> getPatientNotes(@RequestParam Integer patientId);

    @GetMapping(value = "/note/{id}")
    NoteBean getNoteById(@PathVariable String id);

    @PostMapping(value = "/createPatientNote")
    NoteBean createPatientNote(@RequestBody NoteBean note);

    @PutMapping(value = "/note/{id}/update")
    NoteBean updatePatientNote(@PathVariable("id") String id, @RequestBody NoteBean note);
}
