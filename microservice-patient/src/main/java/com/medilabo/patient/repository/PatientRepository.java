package com.medilabo.patient.repository;

import com.medilabo.patient.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    Page<Patient> findAllByOrderByLastnameAsc(Pageable pageable);

}
