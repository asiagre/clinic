package com.project.clinic.validator;

import com.project.clinic.service.AppointmentService;
import com.project.clinic.service.DoctorService;
import com.project.clinic.service.PatientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {

    @InjectMocks
    private Validator validator;

    @Mock
    private PatientService patientService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private AppointmentService appointmentService;

    public boolean validatePatientId(Long id) {
        return patientService.existsById(id);
    }

    public boolean validateDoctorId(Long id) {
        return doctorService.existsById(id);
    }

    public boolean validateAppointmentId(Long id) {
        return appointmentService.existsById(id);
    }

    @Test
    public void shouldalidatePatientId() {
        //Given
        when(patientService.existsById(anyLong())).thenReturn(true);

        //When
        boolean exists = validator.validatePatientId(1L);

        //Then
        Assert.assertTrue(exists);
    }

    @Test
    public void shouldValidateDoctorId() {
        //Given
        when(doctorService.existsById(anyLong())).thenReturn(true);

        //When
        boolean exists = validator.validateDoctorId(1L);

        //Then
        Assert.assertTrue(exists);
    }

    @Test
    public void shouldValidateAppointmentId() {
        //Given
        when(appointmentService.existsById(anyLong())).thenReturn(true);

        //When
        boolean exists = validator.validateAppointmentId(1L);

        //Then
        Assert.assertTrue(exists);
    }
}