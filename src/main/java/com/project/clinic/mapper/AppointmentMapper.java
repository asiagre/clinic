package com.project.clinic.mapper;

import com.project.clinic.domain.Appointment;
import com.project.clinic.domain.Doctor;
import com.project.clinic.domain.Patient;
import com.project.clinic.dto.AppointmentDto;
import com.project.clinic.dto.DoctorDto;
import com.project.clinic.repository.DoctorRepository;
import com.project.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    public AppointmentDto mapToAppointmentDto(final Appointment appointment) {
        return new AppointmentDto(appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getVisitDate());
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
