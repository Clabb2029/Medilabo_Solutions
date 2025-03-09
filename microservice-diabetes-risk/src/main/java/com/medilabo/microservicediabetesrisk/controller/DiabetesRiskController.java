package com.medilabo.microservicediabetesrisk.controller;

import com.medilabo.microservicediabetesrisk.bean.ReportBean;
import com.medilabo.microservicediabetesrisk.service.DiabetesRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DiabetesRiskController {

    private final DiabetesRiskService riskService;

    public DiabetesRiskController(DiabetesRiskService riskService) {
        this.riskService = riskService;
    }

    @GetMapping("/patientDiabetesReport/{patientId}")
    public ResponseEntity<ReportBean> getPatientDiabetesReportById(@PathVariable("patientId") Integer patientId) {
        log.info("Requête reçue pour générer le rapport de diabète du patient avec l'ID : {}", patientId);
        return ResponseEntity.ok(riskService.getPatientDiabetesReportById(patientId));
    }
}
