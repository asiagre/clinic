package com.project.clinic.repository;

import com.project.clinic.domain.Patient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void shouldSavePatient() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");

        //When
        patientRepository.save(patient);
        Patient patientFromDb = patientRepository.findAll().get(0);

        //Then
        Assert.assertEquals("Kowalski", patientFromDb.getLastname());

        //CleanUp
        patientRepository.deleteById(patientFromDb.getId());
    }

    @Test
    public void shouldGetAllPatients() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        patientRepository.save(patient);

        //When
        List<Patient> patients = patientRepository.findAll();

        //Then
        Assert.assertEquals(1, patients.size());
        Assert.assertEquals("Kowalski", patients.get(0).getLastname());

        //CleanUp
        patientRepository.deleteById(patients.get(0).getId());
    }

    @Test
    public void shouldGetPatientByLastname() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        patientRepository.save(patient);

        //When
        List<Patient> patients = patientRepository.retrievePatientsWhereLastnameFragmentIs("owa");
        List<Patient> emptyList = patientRepository.retrievePatientsWhereLastnameFragmentIs("sfn");

        //Then
        Assert.assertEquals(1, patients.size());
        Assert.assertEquals("Kowalski", patients.get(0).getLastname());
        Assert.assertEquals(0, emptyList.size());

        //CleanUp
        patientRepository.deleteById(patients.get(0).getId());
    }

    @Test
    public void shouldGetPatientById() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        patientRepository.save(patient);

        //When
        Long id = patientRepository.findAll().get(0).getId();
        Patient patientFromDb = patientRepository.findById(id).get();

        //Then
        Assert.assertEquals("Kowalski", patientFromDb.getLastname());

        //CleanUp
        patientRepository.deleteById(id);
    }

    @Test
    public void shouldDeletePatient() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        patientRepository.save(patient);

        //When
        List<Patient> patientsBeforeRemoval = patientRepository.findAll();
        patientRepository.deleteById(patientsBeforeRemoval.get(0).getId());
        List<Patient> patientsAfterRemoval = patientRepository.findAll();

        //Then
        Assert.assertEquals(1, patientsBeforeRemoval.size());
        Assert.assertEquals(0, patientsAfterRemoval.size());

    }

    @Test
    public void shouldCheckIfPatientExists() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        patientRepository.save(patient);

        //When
        List<Patient> patients = patientRepository.findAll();
        boolean exists1 = patientRepository.existsById(patients.get(0).getId());
        boolean exists2 = patientRepository.existsById(134L);

        //Then
        Assert.assertTrue(exists1);
        Assert.assertFalse(exists2);

        //CleanUp
        patientRepository.deleteById(patients.get(0).getId());
    }

}