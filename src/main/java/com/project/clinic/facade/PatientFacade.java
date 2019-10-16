package com.project.clinic.facade;

import com.project.clinic.dto.PatientDto;
import com.project.clinic.mapper.PatientMapper;
import com.project.clinic.service.PatientService;
import com.project.clinic.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientFacade {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private Validator validator;

    public List<PatientDto> getAllPatients() {
        return patientMapper.mapToPatientDtoList(patientService.getAllPatients());
    }

    public List<PatientDto> getPatientByLastname(String lastname) {
        return patientMapper.mapToPatientDtoList(patientService.getPatientByLastname(lastname));
    }

    public PatientDto getPatientById(Long patientId) {
        validator.validatePatientId(patientId);
        return patientMapper.mapToPatientDto(patientService.getPatientById(patientId));
    }

    public PatientDto savePatient(PatientDto patientDto) {
        return patientMapper.mapToPatientDto(patientService.savePatient(patientMapper.mapToPatient(patientDto)));
    }

    public void deletePatient(Long patientId) {
        validator.validatePatientId(patientId);
        patientService.deletePatient(patientId);
    }
}
