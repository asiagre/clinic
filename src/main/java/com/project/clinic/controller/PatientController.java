package com.project.clinic.controller;

import com.project.clinic.dto.PatientDto;
import com.project.clinic.exception.IdNotFoundException;
import com.project.clinic.facade.PatientFacade;
import com.project.clinic.service.PatientService;
import com.project.clinic.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/clinic")
public class PatientController {

    @Autowired
    private PatientFacade patientFacade;

    @RequestMapping(method = RequestMethod.GET, value = "patients")
    public List<PatientDto> getAllPatients() {
        return patientFacade.getAllPatients();
    }

    @RequestMapping(method = RequestMethod.GET, value = "patients/lastname")
    public List<PatientDto> getPatientsByLastName(@RequestParam String lastname) {
        return patientFacade.getPatientByLastname(lastname);
    }

    @RequestMapping(method = RequestMethod.GET, value = "patients/{id}")
    public PatientDto getPatientById(@PathVariable Long id) {
        return patientFacade.getPatientById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "patients", consumes = APPLICATION_JSON_VALUE)
    public PatientDto addPatient(@RequestBody PatientDto patientDto) {
        return patientFacade.savePatient(patientDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "patients/{id}", consumes = APPLICATION_JSON_VALUE)
    public PatientDto editPatient(@PathVariable Long id, @RequestBody PatientDto patientDto) {
        if(patientDto.getId().equals(id)) {
            return patientFacade.savePatient(patientDto);
        } else {
            throw new IdNotFoundException("Ids do not match");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "patients/{id}")
    public void removePatient(@PathVariable Long id) {
        patientFacade.deletePatient(id);
    }

}
