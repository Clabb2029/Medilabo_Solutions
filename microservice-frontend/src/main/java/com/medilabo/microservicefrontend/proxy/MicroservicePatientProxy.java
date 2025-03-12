package com.medilabo.microservicefrontend.proxy;

import com.medilabo.microservicefrontend.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservice-patient", url = "${frontend.patient-url}")
public interface MicroservicePatientProxy {

    @GetMapping(value = "/patients")
    PagedModel<PatientBean> getPatients(@RequestParam Integer page);

    @GetMapping(value = "/patient/{id}")
    PatientBean getPatientById(@PathVariable("id") Integer id);

    @PostMapping(value = "/createPatient")
    PatientBean createPatient(@RequestBody PatientBean patient);

    @PutMapping(value = "/patient/{id}/update")
    PatientBean updatePatient(@PathVariable("id") Integer id, @RequestBody PatientBean patient);

    @DeleteMapping(value = "/patient/{id}/delete")
    Void deletePatientById(@PathVariable("id") Integer id);

}
