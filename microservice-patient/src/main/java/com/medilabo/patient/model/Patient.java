package com.medilabo.patient.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

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
    private String lastname;

    @Column
    private String firstname;

    @Column
    private LocalDate birthdate;

    @Column
    private String gender;

    @Column
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

}
