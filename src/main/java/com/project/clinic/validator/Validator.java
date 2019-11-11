package com.project.clinic.validator;

import com.project.clinic.exception.IdNotFoundException;
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

    public void validatePatientId(Long id) {
        if(!patientService.existsById(id)) {
            throw new IdNotFoundException("Wrong patient id");
        }
    }

    public void validateDoctorId(Long id) {
        if(!doctorService.existsById(id)) {
            throw new IdNotFoundException("Wrong doctor id");
        }
    }

    public void validateAppointmentId(Long id) {
        if(!appointmentService.existsById(id)) {
            throw new IdNotFoundException("Wrong appointment id");
        }
    }
}
