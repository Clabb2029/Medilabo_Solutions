package com.medilabo.microservicediabetesrisk.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportBean {

    private String riskLevel;
    private String ruleDescription;
    private List<String> triggers;

}
