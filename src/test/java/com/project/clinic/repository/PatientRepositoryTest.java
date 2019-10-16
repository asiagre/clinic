package com.project.clinic.repository;

import com.project.clinic.domain.Patient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

//    public List<PatientDto> getAllPatients() {
//        return patientMapper.mapToPatientDtoList(patientRepository.findAll());
//    }
//
//    public List<PatientDto> getPatientByLastname(String lastname) {
//        return patientMapper.mapToPatientDtoList(patientRepository.retrievePatientsWhereLastnameFragmentIs(lastname));
//    }
//
//    public PatientDto getPatientById(Long patientId) {
//        return patientMapper.mapToPatientDto(patientRepository.findById(patientId).get());
//    }
//
//    public void deletePatient(Long patientId) {
//        patientRepository.deleteById(patientId);
//    }
//
//    public boolean existsById(Long patientId) {
//        return patientRepository.existsById(patientId);
//    }

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

}