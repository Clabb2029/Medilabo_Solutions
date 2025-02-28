package com.medilabo.microservicefrontend.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PatientBean {

    private Integer id;

    @NotBlank(message = "Veuillez indiquer le nom de famille")
    @Size(max = 100, message = "Le nom de famille doit faire 100 caractères au maximum")
    private String lastname;

    @NotBlank(message = "Veuillez indiquer le prénom")
    @Size(max = 100, message = "Le prénom doit faire 100 caractères au maximum")
    private String firstname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Veuillez indiquer la date de naissance")
    private LocalDate birthdate;

    @NotBlank(message = "Veuillez sélectionner le genre")
    private String gender;

    @Size(max = 255, message = "L'adresse doit faire 255 caractères au maximum")
    private String address;

    @Size(max = 50, message = "Le numéro de téléphone doit faire 50 caractères au maximum")
    private String phoneNumber;

}
