package com.project.clinic.mapper;

import com.project.clinic.domain.Appointment;
import com.project.clinic.domain.Doctor;
import com.project.clinic.domain.Patient;
import com.project.clinic.dto.AppointmentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    public AppointmentDto mapToAppointmentDto(final Appointment appointment) {
        return new AppointmentDto.AppointmentDtoBuilder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .visitDate(appointment.getVisitDate())
                .build();
    }

    public Appointment mapToAppointment(final AppointmentDto appointmentDto, final Doctor doctor, Patient patient) {
        return new Appointment(appointmentDto.getId(), doctor, patient, appointmentDto.getVisitDate());
    }

    public List<AppointmentDto> mapToAppointmentDtoList(final List<Appointment> appointments) {
        return appointments.stream()
                .map(appointment -> mapToAppointmentDto(appointment))
                .collect(Collectors.toList());
    }
}
