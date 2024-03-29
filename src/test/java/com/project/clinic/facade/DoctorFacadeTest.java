package com.project.clinic.facade;

import com.project.clinic.domain.Doctor;
import com.project.clinic.dto.DoctorDto;
import com.project.clinic.mapper.DoctorMapper;
import com.project.clinic.service.DoctorService;
import com.project.clinic.validator.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DoctorFacadeTest {

    @InjectMocks
    private DoctorFacade doctorFacade;

    @Mock
    private DoctorService doctorService;

    @Mock
    private DoctorMapper doctorMapper;

    @Mock
    private Validator validator;

    @Test
    public void shouldGetEmptyList() {
        //Given
        List<DoctorDto> doctorDtos = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        when(doctorService.getAllDoctors()).thenReturn(doctors);
        when(doctorMapper.mapToDoctorDtoList(doctors)).thenReturn(doctorDtos);

        //When
        List<DoctorDto> doctorDtosFromFacade = doctorFacade.getAllDoctors();

        //Then
        Assert.assertEquals(0, doctorDtosFromFacade.size());
    }

    @Test
    public void shouldGetAllDoctors() {
        //Given
        List<DoctorDto> doctorDtos = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorDtos.add(doctorDto);
        doctors.add(doctor);
        when(doctorService.getAllDoctors()).thenReturn(doctors);
        when(doctorMapper.mapToDoctorDtoList(doctors)).thenReturn(doctorDtos);

        //When
        List<DoctorDto> doctorDtosFromFacade = doctorFacade.getAllDoctors();

        //Then
        Assert.assertEquals(1, doctorDtosFromFacade.size());
        Assert.assertEquals("Śliwiński", doctorDtosFromFacade.get(0).getLastname());
    }

    @Test
    public void shouldFindDoctorsByLastname() {
        //Given
        List<DoctorDto> doctorDtos = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorDtos.add(doctorDto);
        doctors.add(doctor);
        when(doctorService.findDoctorsByLastname(anyString())).thenReturn(doctors);
        when(doctorMapper.mapToDoctorDtoList(doctors)).thenReturn(doctorDtos);

        //When
        List<DoctorDto> doctorDtosFromFacade = doctorFacade.findDoctorsByLastname("iwi");

        //Then
        Assert.assertEquals(1, doctorDtosFromFacade.size());
        Assert.assertEquals("Śliwiński", doctorDtosFromFacade.get(0).getLastname());
    }

    @Test
    public void shouldFindDoctorsBySpecialization() {
        //Given
        List<DoctorDto> doctorDtos = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        doctorDtos.add(doctorDto);
        doctors.add(doctor);
        when(doctorService.findDoctorsBySpecialization(anyString())).thenReturn(doctors);
        when(doctorMapper.mapToDoctorDtoList(doctors)).thenReturn(doctorDtos);

        //When
        List<DoctorDto> doctorDtosFromFacade = doctorFacade.findDoctorsBySpecialization("GP");

        //Then
        Assert.assertEquals(1, doctorDtosFromFacade.size());
        Assert.assertEquals("Śliwiński", doctorDtosFromFacade.get(0).getLastname());
    }

    @Test
    public void shouldFindDoctorById() {
        //Given
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        when(doctorService.findDoctorById(anyLong())).thenReturn(doctor);
        when(doctorMapper.mapToDoctorDto(doctor)).thenReturn(doctorDto);

        //When
        DoctorDto doctorDtoFromFacade = doctorFacade.findDoctorById(1L);

        //Then
        Assert.assertEquals("Śliwiński", doctorDtoFromFacade.getLastname());
    }

    @Test
    public void shouldSaveDoctor() {
        //Given
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        Doctor doctor = new Doctor("Adam", "Śliwiński", "GP", 5.0);
        when(doctorService.saveDoctor(any(Doctor.class))).thenReturn(doctor);
        when(doctorMapper.mapToDoctor(any(DoctorDto.class))).thenReturn(doctor);
        when(doctorMapper.mapToDoctorDto(any(Doctor.class))).thenReturn(doctorDto);

        //When
        DoctorDto doctorDtoFromFacade = doctorFacade.saveDoctor(doctorDto);

        //Then
        Assert.assertEquals("Śliwiński", doctorDtoFromFacade.getLastname());
    }

    @Test
    public void shouldRemoveDoctor() {
        //Given && When
        doctorFacade.removeDoctor(1L);

        //Then
        verify(doctorService, times(1)).removeDoctor(1L);
    }

    @Test
    public void shouldAddScore() {
        //Given && When
        doctorFacade.addScore(1L, 5);

        //Then
        verify(doctorService, times(1)).addScore(1L, 5);
    }

    @Test
    public void shouldAddFreeSlots() {
        //Given && When
        doctorFacade.addFreeSlots(1L, LocalDateTime.of(2019, 10, 23, 8, 0));

        //Then
        verify(doctorService, times(1)).addFreeSlots(1L, LocalDateTime.of(2019, 10, 23, 8, 0));
    }
}