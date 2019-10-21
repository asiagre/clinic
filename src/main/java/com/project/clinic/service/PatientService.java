package com.project.clinic.service;

import com.project.clinic.domain.Patient;
import com.project.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> getPatientByLastname(String lastname) {
        return patientRepository.retrievePatientsWhereLastnameFragmentIs(lastname);
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId).get();
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long patientId) {
        patientRepository.deleteById(patientId);
    }

    public boolean existsById(Long patientId) {
        return patientRepository.existsById(patientId);
    }

}
