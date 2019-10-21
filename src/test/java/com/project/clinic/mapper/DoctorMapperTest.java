package com.project.clinic.mapper;

import com.project.clinic.domain.Doctor;
import com.project.clinic.dto.DoctorDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DoctorMapperTest {

    @Autowired
    private DoctorMapper doctorMapper;

    @Test
    public void mapToDoctorDto() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);

        //When
        DoctorDto doctorDto = doctorMapper.mapToDoctorDto(doctor);

        //Then
        Assert.assertEquals("Śliwiński", doctorDto.getLastname());
    }

    @Test
    public void mapToDoctor() {
        //Given
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();

        //When
        Doctor doctor = doctorMapper.mapToDoctor(doctorDto);

        //Then
        Assert.assertEquals("Śliwiński", doctor.getLastname());
    }

    @Test
    public void mapToDoctorDtoList() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);

        //When
        List<DoctorDto> doctorDtos = doctorMapper.mapToDoctorDtoList(doctors);

        //Then
        Assert.assertEquals(1, doctorDtos.size());
        Assert.assertEquals("Śliwiński", doctorDtos.get(0).getLastname());
    }
}