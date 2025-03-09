package com.medilabo.microservicediabetesrisk.proxy;

import com.medilabo.microservicediabetesrisk.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-note", url = "http://localhost:8083")
public interface MicroserviceNoteProxy {

    @GetMapping(value = "/patientNotes")
    List<NoteBean> getPatientNotes(@RequestParam Integer patientId);
}
