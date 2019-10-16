package com.project.clinic.controller;

import com.project.clinic.dto.DoctorDto;
import com.project.clinic.exception.IdNotFoundException;
import com.project.clinic.facade.DoctorFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/clinic")
public class DoctorController {

    @Autowired
    private DoctorFacade doctorFacade;

    @RequestMapping(method = RequestMethod.GET, value = "doctors")
    public List<DoctorDto> getAllDoctors() {
        return doctorFacade.getAllDoctors();
    }

    @RequestMapping(method = RequestMethod.GET, value = "doctors/{id}")
    public DoctorDto findDoctorById(@PathVariable Long id) {
        return doctorFacade.findDoctorById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "doctors/lastname")
    public List<DoctorDto> findDoctorsByLastname(@RequestParam String lastname) {
        return doctorFacade.findDoctorsByLastname(lastname);
    }

    @RequestMapping(method = RequestMethod.GET, value = "doctors/specialization")
    public List<DoctorDto> findDoctorsBySpecialization(@RequestParam String specialization) {
        return doctorFacade.findDoctorsBySpecialization(specialization);
    }

    @RequestMapping(method = RequestMethod.POST, value = "doctors", consumes = APPLICATION_JSON_VALUE)
    public DoctorDto addDoctor(@RequestBody DoctorDto doctorDto) {
        return doctorFacade.saveDoctor(doctorDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "doctors/{id}", consumes = APPLICATION_JSON_VALUE)
    public DoctorDto editDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        if (doctorDto.getId() == id) {
            return doctorFacade.saveDoctor(doctorDto);
        } else {
            throw new IdNotFoundException("Ids do not match");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "doctors/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorFacade.removeDoctor(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "doctors/{id}/scores")
    public void addScore(@PathVariable Long id, @RequestParam Integer score) {
        doctorFacade.addScore(id, score);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "doctors/{id}/slots")
    public void addFreeSlot(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime slot) {
        doctorFacade.addFreeSlots(id, slot);
    }
}
