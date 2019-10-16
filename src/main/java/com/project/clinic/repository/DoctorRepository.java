package com.project.clinic.repository;

import com.project.clinic.domain.Doctor;
import com.project.clinic.domain.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(nativeQuery = true)
    List<Doctor> retrieveDoctorsWhereLastnameFragmentIs(@Param("LASTNAME") String lastName);

    List<Doctor> findAllBySpecialization(String specialization);

}
