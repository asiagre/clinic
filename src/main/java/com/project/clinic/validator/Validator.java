package com.project.clinic.validator;

import com.project.clinic.service.AppointmentService;
import com.project.clinic.service.DoctorService;
import com.project.clinic.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
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
}
