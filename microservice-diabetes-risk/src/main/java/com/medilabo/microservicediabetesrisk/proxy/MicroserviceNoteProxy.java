package com.medilabo.microservicediabetesrisk.proxy;

import com.medilabo.microservicediabetesrisk.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-note", url = "${frontend.note-url}")
public interface MicroserviceNoteProxy {

    @GetMapping(value = "/patientNotes")
    List<NoteBean> getPatientNotes(@RequestParam Integer patientId);
}
