package com.project.clinic.controller;

import com.project.clinic.dto.AppointmentDto;
import com.project.clinic.facade.AppointmentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/clinic")
public class AppointmentController {

    @Autowired
    private AppointmentFacade appointmentFacade;

    @RequestMapping(method = RequestMethod.POST, value = "doctors/{doctorId}/appointments", consumes = APPLICATION_JSON_VALUE)
    public AppointmentDto makeAppointment(@PathVariable Long doctorId, @RequestBody AppointmentDto appointmentDto) {
        return appointmentFacade.makeAppointment(appointmentDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "doctors/{doctorId}/appointments/{appointmentId}", consumes = APPLICATION_JSON_VALUE)
    public AppointmentDto editAppointment(@PathVariable Long doctorId, @PathVariable Long appointmentId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime newTime) {
        return appointmentFacade.changeAppointmentDate(doctorId, appointmentId, newTime);
    }

    @RequestMapping(method = RequestMethod.GET, value = "patients/{patientId}/appointments")
    public List<AppointmentDto> getPatientAppointments(@PathVariable Long patientId) {
        return appointmentFacade.getPatientAppointments(patientId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "patients/{patientId}/appointments/{appointmentId}")
    public void removeAppointment(@PathVariable Long patientId, @PathVariable Long appointmentId) {
        appointmentFacade.removeAppointment(appointmentId);
    }
}
