package com.project.clinic.mapper;

import com.project.clinic.domain.Patient;
import com.project.clinic.dto.PatientDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMapper {

    public PatientDto mapToPatientDto(final Patient patient) {
        return new PatientDto.PatientDtoBuilder()
                .id(patient.getId())
                .firstname(patient.getFirstname())
                .lastname(patient.getLastname())
                .pin(patient.getPin())
                .phoneNumber(patient.getPhoneNumber())
                .email(patient.getEmail())
                .build();
        //return new PatientDto(patient.getId(), patient.getFirstname(), patient.getLastname(), patient.getPin(), patient.getPhoneNumber(), patient.getEmail());
    }

    public Patient mapToPatient(final PatientDto patientDto) {
        return new Patient.PatientBuilder()
                .id(patientDto.getId())
                .firstname(patientDto.getFirstname())
                .lastname(patientDto.getLastname())
                .pin(patientDto.getPin())
                .phoneNumber(patientDto.getPhoneNumber())
                .email(patientDto.getEmail())
                .build();
        //return new Patient(patientDto.getId(), patientDto.getFirstname(), patientDto.getLastname(), patientDto.getPin(), patientDto.getPhoneNumber(), patientDto.getEmail());
    }

    public List<PatientDto> mapToPatientDtoList(final List<Patient> patients) {
        return patients.stream()
                .map(patient -> mapToPatientDto(patient))
                .collect(Collectors.toList());
    }
}
