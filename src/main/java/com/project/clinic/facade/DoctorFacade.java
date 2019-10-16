package com.project.clinic.facade;

import com.project.clinic.dto.DoctorDto;
import com.project.clinic.mapper.DoctorMapper;
import com.project.clinic.service.DoctorService;
import com.project.clinic.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DoctorFacade {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private Validator validator;

    public List<DoctorDto> getAllDoctors() {
        return doctorMapper.mapToDoctorDtoList(doctorService.getAllDoctors());
    }

    public List<DoctorDto> findDoctorsByLastname(String lastname) {
        return doctorMapper.mapToDoctorDtoList(doctorService.findDoctorsByLastname(lastname));
    }

    public List<DoctorDto> findDoctorsBySpecialization(String specialization) {
        return doctorMapper.mapToDoctorDtoList(doctorService.findDoctorsBySpecialization(specialization));
    }

    public DoctorDto findDoctorById(Long id) {
        validator.validateDoctorId(id);
        return doctorMapper.mapToDoctorDto(doctorService.findDoctorById(id));
    }

    public DoctorDto saveDoctor(DoctorDto doctorDto) {
        return doctorMapper.mapToDoctorDto(doctorService.saveDoctor(doctorMapper.mapToDoctor(doctorDto)));
    }

    public void removeDoctor(Long id) {
        validator.validateDoctorId(id);
        doctorService.removeDoctor(id);
    }

    public void addScore(Long doctorId, Integer score) {
        validator.validateDoctorId(doctorId);
        doctorService.addScore(doctorId, score);
    }

    public void addFreeSlots(Long doctorId, LocalDateTime slot) {
        validator.validateDoctorId(doctorId);
        doctorService.addFreeSlots(doctorId, slot);
    }
}
