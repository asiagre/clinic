package com.project.clinic.mapper;

import com.project.clinic.domain.Patient;
import com.project.clinic.dto.PatientDto;
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
public class PatientMapperTest {

    @Autowired
    private PatientMapper patientMapper;

    @Test
    public void mapToPatientDto() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");

        //When
        PatientDto patientDto = patientMapper.mapToPatientDto(patient);

        //Then
        Assert.assertEquals("Kowalski", patientDto.getLastname());
    }

    @Test
    public void mapToPatient() {
        //Given
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .build();

        //When
        Patient patient = patientMapper.mapToPatient(patientDto);

        //Then
        Assert.assertEquals("Kowalski", patient.getLastname());
    }

    @Test
    public void mapToPatientDtoList() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        //When
        List<PatientDto> patientDtos = patientMapper.mapToPatientDtoList(patients);

        //Then
        Assert.assertEquals(1, patientDtos.size());
        Assert.assertEquals("Kowalski", patientDtos.get(0).getLastname());
    }
}