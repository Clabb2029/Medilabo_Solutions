package com.medilabo.microservicefrontend.proxy;

import com.medilabo.microservicefrontend.bean.ReportBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-diabetes-risk", url = "${frontend.diabetes-url}")
public interface MicroserviceDiabetesRiskProxy {

    @GetMapping(value = "/patientDiabetesReport/{patientId}")
    ReportBean getPatientDiabetesReportById(@PathVariable("patientId") Integer patientId);
}
