package com.project.clinic.controller;

import com.google.gson.Gson;
import com.project.clinic.dto.PatientDto;
import com.project.clinic.facade.PatientFacade;
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
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientFacade patientFacade;

    @Test
    public void shouldGetEmptyList() throws Exception {
        //Given

        //When&Then
        mockMvc.perform(get("/v1/clinic/patients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetAllPatients() throws Exception {
        //Given
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .password("abcdef")
                .build();
        List<PatientDto> patientDtoList = new ArrayList<>();
        patientDtoList.add(patientDto);
        when(patientFacade.getAllPatients()).thenReturn(patientDtoList);

        //When&Then
        mockMvc.perform(get("/v1/clinic/patients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastname", is("Kowalski")));
    }

    @Test
    public void shouldGetPatientsByLastName() throws Exception{
        //Given
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .password("abcdef")
                .build();
        List<PatientDto> patientDtoList = new ArrayList<>();
        patientDtoList.add(patientDto);
        when(patientFacade.getPatientByLastname(anyString())).thenReturn(patientDtoList);

        //When&Then
        mockMvc.perform(get("/v1/clinic/patients/lastname")
                .param("lastname", "owa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastname", is("Kowalski")));
    }

    @Test
    public void shouldGetPatientById() throws Exception {
        //Given
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .password("abcdef")
                .build();
        when(patientFacade.getPatientById(1L)).thenReturn(patientDto);

        //When&Then
        mockMvc.perform(get("/v1/clinic/patients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", is("Kowalski")));
    }

    @Test
    public void shouldAddPatient() throws Exception{
        //Given
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .password("abcdef")
                .build();
        when(patientFacade.savePatient(ArgumentMatchers.any(PatientDto.class))).thenReturn(patientDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(patientDto);

        //When&Then
        mockMvc.perform(post("/v1/clinic/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", is("Kowalski")));
    }

    @Test
    public void shouldEditPatient() throws Exception{
        //Given
        PatientDto patientDto = new PatientDto.PatientDtoBuilder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowal")
                .pin("56071812345")
                .phoneNumber("536192836")
                .email("jan.kowalski@test.pl")
                .password("abcdef")
                .build();
        when(patientFacade.savePatient(ArgumentMatchers.any(PatientDto.class))).thenReturn(patientDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(patientDto);

        //When&Then
        mockMvc.perform(put("/v1/clinic/patients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.lastname", is("Kowal")));
    }

    @Test
    public void shouldRemovePatient() throws Exception {
        //Given

        //When&Then
        mockMvc.perform(delete("/v1/clinic/patients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}