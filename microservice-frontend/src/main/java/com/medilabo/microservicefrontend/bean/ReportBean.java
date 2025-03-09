package com.medilabo.microservicefrontend.bean;

import lombok.Data;

import java.util.List;

@Data
public class ReportBean {

    private String riskLevel;
    private String ruleDescription;
    private List<String> triggers;

}