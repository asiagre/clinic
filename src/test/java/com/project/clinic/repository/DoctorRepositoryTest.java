package com.project.clinic.repository;

import com.project.clinic.domain.Doctor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void shouldSaveDoctor() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 0.0);

        //When
        doctorRepository.save(doctor);
        Doctor doctorFromDb = doctorRepository.findAll().get(0);

        //Then
        Assert.assertEquals("Śliwiński", doctorFromDb.getLastname());

        //CleanUp
        doctorRepository.deleteById(doctorFromDb.getId());
    }

    @Test
    public void shouldFindAllDoctors() {
        //Given
        Doctor doctor1 = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        Doctor doctor2 = new Doctor("Jan", "Krzysztofik", "PEDIATRICS", 4.0);
        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);

        //When
        List<Doctor> doctors = doctorRepository.findAll();

        //Then
        Assert.assertEquals(2, doctors.size());
        Assert.assertEquals("Śliwiński", doctors.get(0).getLastname());
        Assert.assertEquals("Jan", doctors.get(1).getFirstname());

        //CleanUp
        doctorRepository.deleteById(doctors.get(0).getId());
        doctorRepository.deleteById(doctors.get(1).getId());
    }

    @Test
    public void shouldFindDoctorById() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorRepository.save(doctor);

        //When
        Long id = doctorRepository.findAll().get(0).getId();
        Doctor doctorFromDb = doctorRepository.findById(id).get();

        //Then
        Assert.assertEquals("Śliwiński", doctorFromDb.getLastname());

        //CleanUp
        doctorRepository.deleteById(id);
    }

    @Test
    public void shouldFindDoctorByLastName() {
        //Given
        Doctor doctor1 = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        Doctor doctor2 = new Doctor("Jan", "Krzysztofik", "PEDIATRICS", 4.0);
        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);

        //When
        List<Doctor> doctors1 = doctorRepository.retrieveDoctorsWhereLastnameFragmentIs("i");
        List<Doctor> doctors2 = doctorRepository.retrieveDoctorsWhereLastnameFragmentIs("Śliw");
        List<Doctor> doctors3 = doctorRepository.retrieveDoctorsWhereLastnameFragmentIs("ac");

        //Then
        Assert.assertEquals(2, doctors1.size());
        Assert.assertEquals(1, doctors2.size());
        Assert.assertEquals(0, doctors3.size());
        Assert.assertEquals("Śliwiński", doctors2.get(0).getLastname());

        //CleanUp
        doctorRepository.deleteById(doctors1.get(1).getId());
        doctorRepository.deleteById(doctors1.get(0).getId());
    }

    @Test
    public void shouldFindDoctorBySpecialization() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorRepository.save(doctor);

        //When
        List<Doctor> doctors = doctorRepository.findAllBySpecialization("GP");
        Doctor doctorFromDb = doctors.get(0);

        //Then
        Assert.assertEquals(1, doctors.size());
        Assert.assertEquals("Adam", doctorFromDb.getFirstname());

        //CleanUp
        doctorRepository.deleteById(doctorFromDb.getId());
    }

    @Test
    public void shouldRemoveDoctor() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorRepository.save(doctor);

        //When
        List<Doctor> doctors = doctorRepository.findAll();
        doctorRepository.deleteById(doctors.get(0).getId());
        List<Doctor> doctorsAfterRemoving = doctorRepository.findAll();

        //Then
        Assert.assertEquals(1, doctors.size());
        Assert.assertEquals(0, doctorsAfterRemoving.size());
    }

    @Test
    public void shouldFindDoctorIfExist() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorRepository.save(doctor);

        //When
        List<Doctor> doctors = doctorRepository.findAll();
        boolean exists1 = doctorRepository.existsById(doctors.get(0).getId());
        boolean exists2 = doctorRepository.existsById(12345L);

        //Then
        Assert.assertTrue(exists1);
        Assert.assertFalse(exists2);

        //CleanUp
        doctorRepository.deleteById(doctors.get(0).getId());
    }

    @Test
    public void shouldAddScores() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorRepository.save(doctor);

        //When
        Doctor doctorFromDb = doctorRepository.findAll().get(0);
        doctorFromDb.getScores().add(5);
        doctorFromDb.getScores().add(3);
        Doctor doctorAfterAddingScore = doctorRepository.save(doctorFromDb);

        //Then
        Assert.assertEquals(2, doctorAfterAddingScore.getScores().size());

        //CleanUp
        doctorRepository.deleteById(doctorFromDb.getId());
    }

    @Test
    public void shouldAddSlots() {
        //Given
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorRepository.save(doctor);

        //When
        Doctor doctorFromDb = doctorRepository.findAll().get(0);
        doctorFromDb.getSlots().add(LocalDateTime.now());
        doctorFromDb.getSlots().add(LocalDateTime.of(2019, 10, 19, 8, 0));
        Doctor doctorAfterAddingSlots = doctorRepository.save(doctorFromDb);

        //Then
        Assert.assertEquals(2, doctorAfterAddingSlots.getSlots().size());

        //CleanUp
        doctorRepository.deleteById(doctorFromDb.getId());
    }
}