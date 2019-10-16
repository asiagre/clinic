package com.project.clinic.controller;

import com.project.clinic.dto.AppointmentDto;
import com.project.clinic.exception.IdNotFoundException;
import com.project.clinic.facade.AppointmentFacade;
import com.project.clinic.service.AppointmentService;
import com.project.clinic.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/clinic")
public class AppointmentController {

    @Autowired
    private AppointmentFacade appointmentFacade;

    @Autowired
    private Validator validator;

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(method = RequestMethod.POST, value = "doctors/{doctorId}/appointments", consumes = APPLICATION_JSON_VALUE)
    public AppointmentDto makeAppointment(@PathVariable Long doctorId, @RequestParam Long patientId, @RequestBody AppointmentDto appointmentDto) {
        return appointmentFacade.makeAppointment(appointmentDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "patients/{patientId}/appointments/{appointmentId}", consumes = APPLICATION_JSON_VALUE)
    public AppointmentDto editAppointment(@PathVariable Long patientId, @PathVariable Long appointmentId, @RequestBody AppointmentDto appointmentDto) {
        validator.validatePatientId(patientId);
        validator.validateAppointmentId(appointmentId);
        if(appointmentId.equals(appointmentDto.getId())) {
            return appointmentService.changeAppointmentDate(appointmentDto);
        } else {
            throw new IdNotFoundException("Ids do not match");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "patients/{patientId}/appointments")
    public List<AppointmentDto> getPatientAppointments(@PathVariable Long patientId) {
        validator.validatePatientId(patientId);
        return appointmentService.getPatientAppointments(patientId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "patients/{patientId}/appointments/{appointmentId}")
    public void removeAppointment(@PathVariable Long patientId, @PathVariable Long appointmentId) {
        validator.validatePatientId(patientId);
        validator.validateAppointmentId(appointmentId);
        appointmentService.removeAppointment(appointmentId);
    }
}
