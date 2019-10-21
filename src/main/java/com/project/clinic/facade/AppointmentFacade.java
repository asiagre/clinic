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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppointmentFacade {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private Validator validator;

    public AppointmentDto makeAppointment(AppointmentDto appointmentDto) {
        validator.validateDoctorId(appointmentDto.getDoctorId());
        validator.validatePatientId(appointmentDto.getPatientId());
        Doctor doctor = doctorService.findDoctorById(appointmentDto.getDoctorId());
        Patient patient = patientService.getPatientById(appointmentDto.getPatientId());
        Appointment appointment = appointmentService.makeAppointment(appointmentMapper.mapToAppointment(appointmentDto, doctor, patient));
        return appointmentMapper.mapToAppointmentDto(appointment);
    }

    public AppointmentDto changeAppointmentDate(Long doctorId, Long appointmentId, LocalDateTime newTime) {
        validator.validateAppointmentId(appointmentId);
        return appointmentMapper.mapToAppointmentDto(appointmentService.changeAppointmentDate(doctorId, appointmentId, newTime));
    }

    public List<AppointmentDto> getPatientAppointments(Long patientId) {
        validator.validatePatientId(patientId);
        return appointmentMapper.mapToAppointmentDtoList(appointmentService.getPatientAppointments(patientId));
    }

    public void removeAppointment(Long id) {
        validator.validateAppointmentId(id);
        appointmentService.removeAppointment(id);
    }

}
