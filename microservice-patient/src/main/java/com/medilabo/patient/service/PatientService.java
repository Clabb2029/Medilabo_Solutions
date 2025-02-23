package com.medilabo.patient.service;

import com.medilabo.patient.exception.PatientNotFoundException;
import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Page<Patient> getAllPatients(PageRequest pageRequest) {
        return patientRepository.findAll(pageRequest);
    }

    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Integer id, Patient patient) {
        Patient existingPatient = patientRepository.findById(id).orElseThrow(null);
        if (existingPatient != null) {
            existingPatient.setLastname(patient.getLastname());
            existingPatient.setFirstname(patient.getFirstname());
            existingPatient.setBirthdate(patient.getBirthdate());
            existingPatient.setGender(patient.getGender());
            existingPatient.setAddress(patient.getAddress());
            existingPatient.setPhoneNumber(patient.getPhoneNumber());
            return patientRepository.save(existingPatient);
        } else {
            throw new PatientNotFoundException("Patient with id " + id + " not found");
        }
    }

    public void deletePatient(Integer id) {
        Patient existingPatient = patientRepository.findById(id).orElseThrow(null);
        if(existingPatient != null) {
            patientRepository.deleteById(id);
        } else {
            throw new PatientNotFoundException("Patient with id " + id + " not found");
        }
    }
}
