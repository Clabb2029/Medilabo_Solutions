package com.medilabo.microservicediabetesrisk.proxy;

import com.medilabo.microservicediabetesrisk.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservice-patient", url = "${frontend.patient-url}")
public interface MicroservicePatientProxy {

    @GetMapping(value = "/patient/{id}")
    PatientBean getPatientById(@PathVariable("id") Integer id);
}
