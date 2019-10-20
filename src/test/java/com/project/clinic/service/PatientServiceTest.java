package com.project.clinic.service;

import com.project.clinic.domain.Patient;
import com.project.clinic.repository.PatientRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Test
    public void shouldGetAllPatients() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        when(patientRepository.findAll()).thenReturn(patients);

        //When
        List<Patient> patientsFromService = patientService.getAllPatients();

        //Then
        Assert.assertEquals(1, patientsFromService.size());
        Assert.assertEquals("Kowalski", patientsFromService.get(0).getLastname());
    }

    @Test
    public void shouldGetEmptyList() {
        //Given
        List<Patient> patients = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(patients);

        //When
        List<Patient> patientsFromService = patientService.getAllPatients();

        //Then
        Assert.assertEquals(0, patientsFromService.size());
    }

    @Test
    public void shouldGetPatientByLastname() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        when(patientRepository.retrievePatientsWhereLastnameFragmentIs(ArgumentMatchers.anyString())).thenReturn(patients);

        //When
        List<Patient> patientsFromService = patientService.getPatientByLastname("owa");

        //Then
        Assert.assertEquals(1, patientsFromService.size());
        Assert.assertEquals("Kowalski", patientsFromService.get(0).getLastname());
    }

    @Test
    public void shouldGetPatientById() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        //When
        Patient patientFromService = patientService.getPatientById(1L);

        //Then
        Assert.assertEquals("Kowalski", patientFromService.getLastname());
    }

    @Test
    public void shouldSavePatient() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        when(patientRepository.save(patient)).thenReturn(patient);

        //When
        Patient patientFromService = patientService.savePatient(patient);

        //Then
        Assert.assertEquals("Kowalski", patientFromService.getLastname());
    }

    @Test
    public void shouldDeletePatient() {
        //Given&When
        patientService.deletePatient(1L);

        //Then
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldCheckIfExistsById() {
        //Given
        when(patientRepository.existsById(1L)).thenReturn(true);

        //When
        boolean exists = patientService.existsById(1L);

        //Then
        Assert.assertTrue(exists);
    }
}