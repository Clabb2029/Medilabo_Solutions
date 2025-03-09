package com.medilabo.microservicediabetesrisk.bean;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientBean {

    private Integer id;
    private LocalDate birthdate;
    private String gender;

}
