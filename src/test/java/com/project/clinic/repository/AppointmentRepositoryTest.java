package com.project.clinic.repository;

import com.project.clinic.domain.Appointment;
import com.project.clinic.domain.Doctor;
import com.project.clinic.domain.Patient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void shouldSaveAppointment() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));

        //When
        appointmentRepository.save(appointment);
        Appointment appointmentFromDb = appointmentRepository.findAll().get(0);

        //Then
        Assert.assertEquals("Śliwiński", appointmentFromDb.getDoctor().getLastname());
        Assert.assertEquals("Kowalski", appointmentFromDb.getPatient().getLastname());

        //CleanUp
        appointmentRepository.deleteById(appointmentFromDb.getId());
        patientRepository.deleteById(appointment.getPatient().getId());
        doctorRepository.deleteById(appointment.getDoctor().getId());
    }

    @Test
    public void shouldFindAppointmentById() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));
        appointmentRepository.save(appointment);

        //When
        Long id = appointmentRepository.findAll().get(0).getId();
        Appointment appointmentFromDb = appointmentRepository.findById(id).get();

        //Then
        Assert.assertEquals("Śliwiński", appointmentFromDb.getDoctor().getLastname());
        Assert.assertEquals("Kowalski", appointmentFromDb.getPatient().getLastname());

        //CleanUp
        appointmentRepository.deleteById(id);
        patientRepository.deleteById(appointment.getPatient().getId());
        doctorRepository.deleteById(appointment.getDoctor().getId());
    }

    @Test
    public void shouldFindAllAppointments() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));
        appointmentRepository.save(appointment);

        //When
        List<Appointment> appointmentList = appointmentRepository.findAll();

        //Then
        Assert.assertEquals("Śliwiński", appointmentList.get(0).getDoctor().getLastname());
        Assert.assertEquals("Kowalski", appointmentList.get(0).getPatient().getLastname());

        //CleanUp
        appointmentRepository.deleteById(appointmentList.get(0).getId());
        patientRepository.deleteById(appointment.getPatient().getId());
        doctorRepository.deleteById(appointment.getDoctor().getId());
    }

    @Test
    public void shouldRemoveAppointment() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));
        appointmentRepository.save(appointment);

        //When
        List<Appointment> appointmentsBeforeRemoval = appointmentRepository.findAll();
        Long id = appointmentsBeforeRemoval.get(0).getId();
        appointmentRepository.deleteById(id);
        patientRepository.deleteById(appointment.getPatient().getId());
        doctorRepository.deleteById(appointment.getDoctor().getId());
        List<Appointment> appointmentsAfterRemoval = appointmentRepository.findAll();

        //Then
        Assert.assertEquals(1, appointmentsBeforeRemoval.size());
        Assert.assertEquals(0, appointmentsAfterRemoval.size());
    }

    @Test
    public void shouldFindIfExists() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        Appointment appointment = new Appointment(doctor, patient, LocalDateTime.of(2019, 10, 19, 8, 0));
        appointmentRepository.save(appointment);

        //When
        List<Appointment> appointmentList = appointmentRepository.findAll();
        boolean exists1 = appointmentRepository.existsById(appointmentList.get(0).getId());
        boolean exists2 = appointmentRepository.existsById(293L);

        //Then
        Assert.assertTrue(exists1);
        Assert.assertFalse(exists2);

        //CleanUp
        appointmentRepository.deleteById(appointmentList.get(0).getId());
        patientRepository.deleteById(appointment.getPatient().getId());
        doctorRepository.deleteById(appointment.getDoctor().getId());
    }

}