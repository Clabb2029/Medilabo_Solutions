package com.medilabo.microservicefrontend.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class NoteBean {

    private String id;

    private Integer patientId;

    @NotBlank(message = "Veuillez remplir la note")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Veuillez indiquer la date")
    private LocalDate date;
}
