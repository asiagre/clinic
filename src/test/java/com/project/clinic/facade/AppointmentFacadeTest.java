package com.project.clinic.facade;

import com.project.clinic.domain.Appointment;
import com.project.clinic.domain.Doctor;
import com.project.clinic.domain.Patient;
import com.project.clinic.dto.AppointmentDto;
import com.project.clinic.mapper.AppointmentMapper;
import com.project.clinic.service.AppointmentService;
import com.project.clinic.service.DoctorService;
import com.project.clinic.service.PatientService;
import com.project.clinic.validator.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentFacadeTest {

    @InjectMocks
    private AppointmentFacade appointmentFacade;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private Validator validator;

    @Test
    public void shouldMakeAppointment() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        Appointment appointment = new Appointment(3L, doctor, patient, LocalDateTime.of(2019, 11, 5, 8, 0));
        AppointmentDto appointmentDto = new AppointmentDto.AppointmentDtoBuilder()
                .id(3L)
                .doctorId(1L)
                .patientId(2L)
                .visitDate(LocalDateTime.of(2019, 11, 5, 8, 0))
                .build();
        when(validator.validateDoctorId(anyLong())).thenReturn(true);
        lenient().when(validator.validateAppointmentId(anyLong())).thenReturn(true);
        when(doctorService.findDoctorById(anyLong())).thenReturn(doctor);
        when(patientService.getPatientById(anyLong())).thenReturn(patient);
        when(appointmentMapper.mapToAppointment(any(AppointmentDto.class), any(Doctor.class), any(Patient.class))).thenReturn(appointment);
        when(appointmentMapper.mapToAppointmentDto(appointment)).thenReturn(appointmentDto);
        when(appointmentService.makeAppointment(any(Appointment.class))).thenReturn(appointment);

        //When
        AppointmentDto appointmentDtoFromFacade = appointmentFacade.makeAppointment(appointmentDto);

        //Then
        Assert.assertEquals(new Long(3), appointmentDtoFromFacade.getId());
    }

    @Test
    public void shouldChangeAppointmentDate() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        AppointmentDto appointmentDto = new AppointmentDto.AppointmentDtoBuilder()
                .id(3L)
                .doctorId(1L)
                .patientId(2L)
                .visitDate(LocalDateTime.of(2019, 11, 5, 8, 0))
                .build();
        Appointment appointment = new Appointment(3L, doctor, patient, LocalDateTime.of(2019, 11, 5, 8, 0));
        when(validator.validateAppointmentId(anyLong())).thenReturn(true);
        when(appointmentService.changeAppointmentDate(anyLong(), anyLong(), any(LocalDateTime.class))).thenReturn(appointment);
        when(appointmentMapper.mapToAppointmentDto(any(Appointment.class))).thenReturn(appointmentDto);

        //When
        AppointmentDto appointmentDtoFromFacade = appointmentFacade.changeAppointmentDate(1L, 3L, LocalDateTime.of(2019, 11, 8, 8, 0));

        //Then
        Assert.assertEquals(new Long(3), appointmentDtoFromFacade.getId());
        Assert.assertEquals(new Long(1), appointmentDtoFromFacade.getDoctorId());
    }

    @Test
    public void shouldGetPatientAppointments() {
        //Given
        Patient patient = new Patient("Jan", "Kowalski", "56071812345", "536192836", "jan.kowalski@test.pl");
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        Appointment appointment = new Appointment(3L, doctor, patient, LocalDateTime.of(2019, 11, 5, 8, 0));
        AppointmentDto appointmentDto = new AppointmentDto.AppointmentDtoBuilder()
                .id(3L)
                .doctorId(1L)
                .patientId(2L)
                .visitDate(LocalDateTime.of(2019, 11, 5, 8, 0))
                .build();
        List<Appointment> appointments = new ArrayList<>();
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        appointments.add(appointment);
        appointmentDtos.add(appointmentDto);
        when(validator.validatePatientId(anyLong())).thenReturn(true);
        when(appointmentService.getPatientAppointments(anyLong())).thenReturn(appointments);
        when(appointmentMapper.mapToAppointmentDtoList(appointments)).thenReturn(appointmentDtos);

        //When
        List<AppointmentDto> appointmentDtosFromFacade = appointmentFacade.getPatientAppointments(2L);

        //Then
        Assert.assertEquals(1, appointmentDtosFromFacade.size());
        Assert.assertEquals(new Long(3), appointmentDtosFromFacade.get(0).getId());
    }

    @Test
    public void shouldRemoveAppointment() {
        //Given
        when(validator.validateAppointmentId(anyLong())).thenReturn(true);

        //When
        appointmentFacade.removeAppointment(1L);

        //Then
        verify(appointmentService, times(1)).removeAppointment(1L);
    }
}