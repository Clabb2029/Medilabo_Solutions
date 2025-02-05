package com.medilabo.patient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "patient")
@Data
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    @NotBlank(message = "Please fill the lastname field")
    @Size(max = 100, message = "The lastname must be a maximum of 100 characters")
    private String lastname;

    @Column
    @NotBlank(message = "Please fill the firstname field")
    @Size(max = 100, message = "The firstname must be a maximum of 100 characters")
    private String firstname;

    @Column
    @NotBlank(message = "Please fill the birthdate field")
    private String birthdate;

    @Column
    @NotBlank(message = "Please fill the gender field")
    private String gender;

    @Column
    @Size(max = 255, message = "The address must be a maximum of 255 characters")
    private String address;

    @Column(name = "phone_number")
    @Size(max = 50, message = "The phone number must be a maximum of 50 characters")
    private String phoneNumber;

}
