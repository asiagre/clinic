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

    public AppointmentDto changeAppointmentDate(AppointmentDto appointmentDto) {
        validator.validateDoctorId(appointmentDto.getDoctorId());
        validator.validatePatientId(appointmentDto.getPatientId());
        Doctor doctor = doctorService.findDoctorById(appointmentDto.getDoctorId());
        Patient patient = patientService.getPatientById(appointmentDto.getPatientId());
        Appointment appointment = appointmentService.changeAppointmentDate(appointmentMapper.mapToAppointment(appointmentDto, doctor, patient));
        return appointmentMapper.mapToAppointmentDto(appointment);
    }
//
//    public List<AppointmentDto> getPatientAppointments(Long patientId) {
//        List<AppointmentDto> appointmentDtos = appointmentMapper.mapToAppointmentDtoList(appointmentRepository.findAll());
//        return appointmentDtos.stream()
//                .filter(appointmentDto -> appointmentDto.getPatientId().equals(patientId))
//                .collect(Collectors.toList());
//    }
//
//    public void removeAppointment(Long id) {
//        Appointment appointment = appointmentRepository.findById(id).get();
//        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).get();
//        Patient patient = patientRepository.findById(appointment.getPatient().getId()).get();
//        patient.getAppointmentList().remove(appointment);
//        doctor.getSlots().add(appointment.getVisitDate());
//        patientRepository.save(patient);
//        doctorRepository.save(doctor);
//        appointmentRepository.deleteById(id);
//    }
//
//    public boolean existsById(Long id) {
//        return appointmentRepository.existsById(id);
//    }
}
