package com.project.clinic.mapper;

import com.project.clinic.domain.Doctor;
import com.project.clinic.dto.DoctorDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorMapper {

    public DoctorDto mapToDoctorDto(final Doctor doctor) {
        return new DoctorDto.DoctorDtoBuilder()
                .id(doctor.getId())
                .firstname(doctor.getFirstname())
                .lastname(doctor.getLastname())
                .specialization(doctor.getSpecialization())
                .rating(doctor.getRating())
                .scores(doctor.getScores())
                .freeSlots(doctor.getSlots())
                .build();

        //return new DoctorDto(doctor.getId(), doctor.getFirstname(), doctor.getLastname(), doctor.getSpecialization(), doctor.getRating(), new ArrayList<>(doctor.getScores()), new ArrayList<>(doctor.getSlots()));
    }

    public Doctor mapToDoctor(final DoctorDto doctorDto) {
        return new Doctor.DoctorBuilder()
                .id(doctorDto.getId())
                .firstname(doctorDto.getFirstname())
                .lastname(doctorDto.getLastname())
                .specialization(doctorDto.getSpecialization())
                .rating(doctorDto.getRating())
                .scores(doctorDto.getScores())
                .freeSlots(doctorDto.getFreeSlots())
                .build();
        //return new Doctor(doctorDto.getId(), doctorDto.getFirstname(), doctorDto.getLastname(), doctorDto.getSpecialization(), doctorDto.getRating(), new ArrayList<>(doctorDto.getScores()), new ArrayList<>());
    }

    public List<DoctorDto> mapToDoctorDtoList(final List<Doctor> doctors) {
        return doctors.stream()
                .map(doctor -> mapToDoctorDto(doctor))
                .collect(Collectors.toList());
    }
}
