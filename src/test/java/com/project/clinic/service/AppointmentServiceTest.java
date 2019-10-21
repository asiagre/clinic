package com.project.clinic.service;

import com.project.clinic.domain.Appointment;
import com.project.clinic.domain.Doctor;
import com.project.clinic.domain.Patient;
import com.project.clinic.repository.AppointmentRepository;
import com.project.clinic.repository.DoctorRepository;
import com.project.clinic.repository.PatientRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Test
    public void shouldMakeAppointment() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);
        doctor.getSlots().add(LocalDateTime.of(2019, 10, 19, 8, 0));
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        //When
        Appointment appointmentFromService = appointmentService.makeAppointment(appointment);

        //Then
        Assert.assertEquals("Kowalski", appointmentFromService.getPatient().getLastname());
        Assert.assertEquals("Śliwiński", appointmentFromService.getDoctor().getLastname());
        Assert.assertEquals(LocalDateTime.of(2019, 10, 19, 8, 0), appointmentFromService.getVisitDate());

    }

    @Test
    public void shouldChangeAppointmentDate() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);
        doctor.getSlots().add(LocalDateTime.of(2019, 10, 19, 9, 0));
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findById(2L)).thenReturn(Optional.of(appointment));
        appointment.setVisitDate(LocalDateTime.of(2019, 10, 19, 9, 0));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        //When
        Appointment appointmentFromService = appointmentService.changeAppointmentDate(1L, 2L, LocalDateTime.of(2019, 10, 19, 9, 0));

        //Then
        Assert.assertEquals("Kowalski", appointmentFromService.getPatient().getLastname());
        Assert.assertEquals("Śliwiński", appointmentFromService.getDoctor().getLastname());
        Assert.assertEquals(LocalDateTime.of(2019, 10, 19, 9, 0), appointmentFromService.getVisitDate());
    }

    @Test
    public void shouldGetPatientAppointments() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);
        Patient patient = new Patient(1L, "Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        when(appointmentRepository.findAll()).thenReturn(appointmentList);

        //When
        List<Appointment> appointmentsFromService = appointmentService.getPatientAppointments(1L);

        //Then
        Assert.assertEquals(1, appointmentsFromService.size());

    }

    @Test
    public void shouldRemoveAppointment() {
        //Given
        Doctor doctor = new Doctor(1L, "Adam", "Śliwiński", "GP", 0.0, new ArrayList<>(), new ArrayList<>());
        Patient patient = new Patient(2L, "Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", new HashSet<>());
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));
        patient.getAppointmentList().add(appointment);
        when(appointmentRepository.findById(3L)).thenReturn(Optional.of(appointment));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));

        //When
        appointmentService.removeAppointment(3L);

        //Then
        verify(appointmentRepository, times(1)).deleteById(3L);
    }

    @Test
    public void shouldCheckIfExistsById() {
        //Given
        when(appointmentRepository.existsById(1L)).thenReturn(true);

        //When
        boolean exists = appointmentRepository.existsById(1L);

        //Then
        Assert.assertTrue(exists);
    }
}