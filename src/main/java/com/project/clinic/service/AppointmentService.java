package com.project.clinic.service;

import com.project.clinic.domain.Appointment;
import com.project.clinic.domain.Doctor;
import com.project.clinic.domain.Patient;
import com.project.clinic.dto.AppointmentDto;
import com.project.clinic.dto.DoctorDto;
import com.project.clinic.exception.SlotsException;
import com.project.clinic.mapper.AppointmentMapper;
import com.project.clinic.mapper.DoctorMapper;
import com.project.clinic.mapper.PatientMapper;
import com.project.clinic.repository.AppointmentRepository;
import com.project.clinic.repository.DoctorRepository;
import com.project.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Appointment makeAppointment(Appointment appointment) {
        Doctor doctor = appointment.getDoctor();
        long numberOfSlots = doctor.getSlots().stream()
                .filter(slot -> slot.equals(appointment.getVisitDate()))
                .count();
        if(numberOfSlots > 0) {
            doctor.getSlots().remove(appointment.getVisitDate());
            doctorRepository.save(doctor);
            return appointmentRepository.save(appointment);
        } else {
            throw new SlotsException("This timeframe is not available");
        }
    }

    public Appointment changeAppointmentDate(Long doctorId, Long appointmentId, LocalDateTime newTime) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        Appointment appointment = appointmentRepository.findById(appointmentId).get();
        long slots = doctor.getSlots().stream()
                .filter(slot -> slot.equals(newTime))
                .count();
        if (slots > 0) {
            LocalDateTime oldVisitTime = appointment.getVisitDate();
            doctor.getSlots().add(oldVisitTime);
            doctor.getSlots().remove(newTime);
            doctorRepository.save(doctor);
            appointment.setVisitDate(newTime);
            return appointmentRepository.save(appointment);
        } else {
            throw new SlotsException("This timeframe is not available");
        }
    }

    public List<Appointment> getPatientAppointments(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .filter(appointment -> appointment.getPatient().getId().equals(patientId))
                .collect(Collectors.toList());
    }

    public void removeAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).get();
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).get();
        Patient patient = patientRepository.findById(appointment.getPatient().getId()).get();
        patient.getAppointmentList().remove(appointment);
        doctor.getSlots().add(appointment.getVisitDate());
        patientRepository.save(patient);
        doctorRepository.save(doctor);
        appointmentRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return appointmentRepository.existsById(id);
    }
}
