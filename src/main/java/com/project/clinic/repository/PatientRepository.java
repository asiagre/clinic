package com.project.clinic.repository;

import com.project.clinic.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(nativeQuery = true)
    List<Patient> retrievePatientsWhereLastnameFragmentIs(@Param("LASTNAME") String lastName);
}
