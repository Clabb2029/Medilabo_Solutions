package com.medilabo.gateway.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "doctor")
public class Doctor {

    @Id
    @Column
    private int id;

    @Column
    private String lastname;

    @Column
    private String firstname;

    @Column
    private String username;

    @Column
    private String password;

}
