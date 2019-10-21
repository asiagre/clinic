package com.project.clinic.controller;

import com.google.gson.Gson;
import com.project.clinic.dto.DoctorDto;
import com.project.clinic.facade.DoctorFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorFacade doctorFacade;

    @Test
    public void shouldGetEmptyList() throws Exception{
        //Given

        //When&Then
        mockMvc.perform(get("/v1/clinic/doctors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetAllDoctors() throws Exception {
        //Given
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        List<DoctorDto> doctorDtos = new ArrayList<>();
        doctorDtos.add(doctorDto);
        when(doctorFacade.getAllDoctors()).thenReturn(doctorDtos);

        //When&Then
        mockMvc.perform(get("/v1/clinic/doctors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastname", is("Śliwiński")));
    }

    @Test
    public void shouldFindDoctorById() throws Exception {
        //Given
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        when(doctorFacade.findDoctorById(1L)).thenReturn(doctorDto);

        //When&Then
        mockMvc.perform(get("/v1/clinic/doctors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", is("Śliwiński")));
    }

    @Test
    public void shouldFindDoctorsByLastname() throws Exception {
        //Given
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        List<DoctorDto> doctorDtos = new ArrayList<>();
        doctorDtos.add(doctorDto);
        when(doctorFacade.findDoctorsByLastname(anyString())).thenReturn(doctorDtos);

        //When&Then
        mockMvc.perform(get("/v1/clinic/doctors/lastname")
                .param("lastname", "iwi")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastname", is("Śliwiński")));
    }

    @Test
    public void shouldFindDoctorsBySpecialization() throws Exception {
        //Given
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        List<DoctorDto> doctorDtos = new ArrayList<>();
        doctorDtos.add(doctorDto);
        when(doctorFacade.findDoctorsBySpecialization("GP")).thenReturn(doctorDtos);

        //When&Then
        mockMvc.perform(get("/v1/clinic/doctors/specialization")
                .param("specialization", "GP")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastname", is("Śliwiński")));
    }

    @Test
    public void shouldAddDoctor() throws Exception {
        //Given
        Gson gson = new Gson();
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwiński")
                .specialization("GP")
                .rating(5.0)
                .build();
        when(doctorFacade.saveDoctor(ArgumentMatchers.any(DoctorDto.class))).thenReturn(doctorDto);
        String jsonContent = gson.toJson(doctorDto);

        //When&Then
        mockMvc.perform(post("/v1/clinic/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", is("Śliwiński")));
    }

    @Test
    public void shouldEditDoctor() throws Exception {
        //Given
        Gson gson = new Gson();
        DoctorDto doctorDto = new DoctorDto.DoctorDtoBuilder()
                .id(1L)
                .firstname("Adam")
                .lastname("Śliwa")
                .specialization("GP")
                .rating(5.0)
                .build();
        when(doctorFacade.saveDoctor(ArgumentMatchers.any(DoctorDto.class))).thenReturn(doctorDto);
        String jsonContent = gson.toJson(doctorDto);

        //When&Then
        mockMvc.perform(put("/v1/clinic/doctors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", is("Śliwa")));
    }

    @Test
    public void shouldDeleteDoctor() throws Exception {
        //Given

        //When&Then
        mockMvc.perform(delete("/v1/clinic/doctors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddScore() throws Exception {
        //Given

        //When&Then
        mockMvc.perform(put("/v1/clinic/doctors/1/scores")
                .param("score", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldAddFreeSlot() throws Exception {
        //Given

        //When&Then
        mockMvc.perform(put("/v1/clinic/doctors/1/slots")
                .param("slot", "2019-10-23T08:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}