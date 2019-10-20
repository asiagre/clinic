package com.project.clinic.service;

import com.project.clinic.domain.Doctor;
import com.project.clinic.repository.DoctorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Test
    public void shouldGetAllDoctors() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        when(doctorRepository.findAll()).thenReturn(doctors);

        //When
        List<Doctor> doctorsFromService = doctorService.getAllDoctors();

        //Then
        Assert.assertEquals(1, doctorsFromService.size());
        Assert.assertEquals("Śliwiński", doctorsFromService.get(0).getLastname());
    }

    @Test
    public void shouldGetEmptyList() {
        //Given
        List<Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctors);

        //When
        List<Doctor> doctorsFromService = doctorService.getAllDoctors();

        //Then
        Assert.assertEquals(0, doctorsFromService.size());
    }

    @Test
    public void shouldFindDoctorsByLastname() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        when(doctorRepository.retrieveDoctorsWhereLastnameFragmentIs(anyString())).thenReturn(doctors);

        //When
        List<Doctor> doctorsFromService = doctorService.findDoctorsByLastname("iwi");

        //Then
        Assert.assertEquals(1, doctorsFromService.size());
        Assert.assertEquals("Śliwiński", doctorsFromService.get(0).getLastname());
    }

    @Test
    public void shouldFindDoctorsBySpecialization() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        when(doctorRepository.findAllBySpecialization("GP")).thenReturn(doctors);

        //When
        List<Doctor> doctorsFromService = doctorService.findDoctorsBySpecialization("GP");

        //Then
        Assert.assertEquals(1, doctorsFromService.size());
        Assert.assertEquals("Śliwiński", doctorsFromService.get(0).getLastname());
    }

    @Test
    public void shouldFindDoctorById() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        //When
        Doctor doctorFromService = doctorService.findDoctorById(1L);

        //Then
        Assert.assertEquals("Śliwiński", doctorFromService.getLastname());
    }

    @Test
    public void shouldSaveDoctor() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        //When
        Doctor doctorFromService = doctorService.saveDoctor(doctor);

        //Then
        Assert.assertEquals("Śliwiński", doctorFromService.getLastname());
    }

    @Test
    public void shouldRemoveDoctor() {
        //Given&When
        doctorService.removeDoctor(1L);

        //Then
        verify(doctorRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldCheckIfExistsById() {
        //Given
        when(doctorRepository.existsById(1L)).thenReturn(true);

        //When
        boolean exists = doctorService.existsById(1L);

        //Then
        Assert.assertTrue(exists);
    }

    @Test
    public void shouldAddScore() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        doctor.getScores().add(5);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        //Given&When
        doctorService.addScore(1L, 5);

        //Then
        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    public void shouldAddFreeSlots() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        //Given&When
        doctorService.addFreeSlots(1L, LocalDateTime.of(2019, 10, 28, 9, 0));

        //Then
        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).save(doctor);

    }
}