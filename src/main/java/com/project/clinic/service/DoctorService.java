package com.project.clinic.service;

import com.project.clinic.domain.Doctor;
import com.project.clinic.dto.DoctorDto;
import com.project.clinic.exception.SlotsException;
import com.project.clinic.mapper.DoctorMapper;
import com.project.clinic.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> findDoctorsByLastname(String lastname) {
        return doctorRepository.retrieveDoctorsWhereLastnameFragmentIs(lastname);
    }

    public List<Doctor> findDoctorsBySpecialization(String specialization) {
        return doctorRepository.findAllBySpecialization(specialization);
    }

    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id).get();
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void removeDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return doctorRepository.existsById(id);
    }

    public void addScore(Long doctorId, Integer score) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        doctor.getScores().add(score);
        updateRating(doctor);
        doctorRepository.save(doctor);
    }

    public void addFreeSlots(Long doctorId, LocalDateTime slot) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        List<LocalDateTime> slots = doctor.getSlots();
        for(LocalDateTime s : slots) {
            if(s.equals(slot)) {
                throw new SlotsException("This slot is already in the list");
            }
        }
        doctor.getSlots().add(slot);
        doctorRepository.save(doctor);
    }

    private void updateRating(Doctor doctor) {
        Integer sum = 0;
        for(Integer s : doctor.getScores()) {
            sum += s;
        }
        double rating = (double) sum / doctor.getScores().size();
        doctor.setRating(rating);
    }
}
