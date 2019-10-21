package com.project.clinic.facade;

import com.project.clinic.domain.Patient;
import com.project.clinic.dto.PatientDto;
import com.project.clinic.mapper.PatientMapper;
import com.project.clinic.service.PatientService;
import com.project.clinic.validator.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientFacadeTest {

    @InjectMocks
    private PatientFacade patientFacade;

    @Mock
    private PatientService patientService;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private Validator validator;

    @Test
    public void shouldGetAllPatients() {
        //Given
        List<PatientDto> patientDtos = new ArrayList<>();
        List<Patient> patients = new ArrayList<>();
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .build();
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        patientDtos.add(patientDto);
        patients.add(patient);
        when(patientService.getAllPatients()).thenReturn(patients);
        when(patientMapper.mapToPatientDtoList(patients)).thenReturn(patientDtos);

        //When
        List<PatientDto> patientDtosFromFacade = patientFacade.getAllPatients();

        //Then
        Assert.assertEquals(1, patientDtosFromFacade.size());
        Assert.assertEquals("Kowalski", patientDtosFromFacade.get(0).getLastname());
    }

    @Test
    public void shouldGetPatientByLastname() {
        //Given
        List<PatientDto> patientDtos = new ArrayList<>();
        List<Patient> patients = new ArrayList<>();
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .build();
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        patientDtos.add(patientDto);
        patients.add(patient);
        when(patientService.getPatientByLastname(anyString())).thenReturn(patients);
        when(patientMapper.mapToPatientDtoList(patients)).thenReturn(patientDtos);

        //When
        List<PatientDto> patientDtosFromFacade = patientFacade.getPatientByLastname("owa");

        //Then
        Assert.assertEquals(1, patientDtosFromFacade.size());
        Assert.assertEquals("Kowalski", patientDtosFromFacade.get(0).getLastname());
    }

    @Test
    public void shouldGetPatientById() {
        //Given
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .build();
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        when(validator.validatePatientId(anyLong())).thenReturn(true);
        when(patientService.getPatientById(anyLong())).thenReturn(patient);
        when(patientMapper.mapToPatientDto(patient)).thenReturn(patientDto);

        //When
        PatientDto patientDtoFromFacade = patientFacade.getPatientById(1L);

        //Then
        Assert.assertEquals("Kowalski", patientDtoFromFacade.getLastname());
    }

    @Test
    public void shouldSavePatient() {
        //Given
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .build();
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        when(patientService.savePatient(patient)).thenReturn(patient);
        when(patientMapper.mapToPatient(any(PatientDto.class))).thenReturn(patient);
        when(patientMapper.mapToPatientDto(any(Patient.class))).thenReturn(patientDto);

        //When
        PatientDto patientDtoFromFacade = patientFacade.savePatient(patientDto);

        //Then
        Assert.assertEquals("Kowalski", patientDtoFromFacade.getLastname());
    }

    @Test
    public void shouldDeletePatient() {
        //Given
        when(validator.validatePatientId(anyLong())).thenReturn(true);

        //When
        patientFacade.deletePatient(1L);

        //Then
        verify(patientService, times(1)).deletePatient(1L);
    }
}