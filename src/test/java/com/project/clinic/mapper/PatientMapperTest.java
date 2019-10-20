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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientMapperTest {

    @Autowired
    private PatientMapper patientMapper;

    @Test
    public void mapToPatientDto() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");

        //When
        PatientDto patientDto = patientMapper.mapToPatientDto(patient);

        //Then
        Assert.assertEquals("Kowalski", patientDto.getLastname());
    }

    @Test
    public void mapToPatient() {
        //Given
        PatientDto patientDto = new PatientDto(1L, "Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");

        //When
        Patient patient = patientMapper.mapToPatient(patientDto);

        //Then
        Assert.assertEquals("Kowalski", patientDto.getLastname());
    }

    @Test
    public void mapToPatientDtoList() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        //When
        List<PatientDto> patientDtos = patientMapper.mapToPatientDtoList(patients);

        //Then
        Assert.assertEquals(1, patientDtos.size());
        Assert.assertEquals("Kowalski", patientDtos.get(0).getLastname());
    }
}