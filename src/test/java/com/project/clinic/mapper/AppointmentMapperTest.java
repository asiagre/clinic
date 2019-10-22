package com.project.clinic.mapper;

import com.project.clinic.domain.Appointment;
import com.project.clinic.domain.Doctor;
import com.project.clinic.domain.Patient;
import com.project.clinic.dto.AppointmentDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentMapperTest {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Test
    public void mapToAppointmentDto() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        Appointment appointment = new Appointment(3L, doctor, patient, LocalDateTime.of(2019, 11, 5, 8, 0));

        //When
        AppointmentDto appointmentDto = appointmentMapper.mapToAppointmentDto(appointment);

        //Then
        Assert.assertEquals(new Long(3), appointmentDto.getId());
    }

    @Test
    public void mapToAppointment() {
        //Given
        AppointmentDto appointmentDto = new AppointmentDto.AppointmentDtoBuilder()
                .id(3L)
                .doctorId(1L)
                .patientId(2L)
                .visitDate(LocalDateTime.of(2019, 11, 5, 8, 0))
                .build();
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);

        //When
        Appointment appointment = appointmentMapper.mapToAppointment(appointmentDto, doctor, patient);

        //Then
        Assert.assertEquals(new Long(3), appointmentDto.getId());
        Assert.assertEquals("Kowalski", appointment.getPatient().getLastname());
        Assert.assertEquals("Śliwiński", appointment.getDoctor().getLastname());
    }

    @Test
    public void mapToAppointmentDtoList() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl", "abcdef");
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        Appointment appointment = new Appointment(3L, doctor, patient, LocalDateTime.of(2019, 11, 5, 8, 0));
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        //When
        List<AppointmentDto> appointmentDtos = appointmentMapper.mapToAppointmentDtoList(appointments);

        //Then
        Assert.assertEquals(1, appointmentDtos.size());
        Assert.assertEquals(new Long(3), appointmentDtos.get(0).getId());
    }
}