package com.project.clinic.controller;

import com.google.gson.*;
import com.project.clinic.dto.AppointmentDto;
import com.project.clinic.facade.AppointmentFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentFacade appointmentFacade;

    @Test
    public void shouldGetEmptyList() throws Exception {
        //Given

        //When&Then
        mockMvc.perform(get("/v1/clinic/patients/1/appointments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldMakeAppointment() throws Exception {
        //Given
        AppointmentDto appointmentDto = new AppointmentDto.AppointmentDtoBuilder()
                .id(1L)
                .doctorId(1L)
                .patientId(3L)
                .visitDate(LocalDateTime.of(2019, 11, 5, 8, 0))
                .build();
        when(appointmentFacade.makeAppointment(ArgumentMatchers.any(AppointmentDto.class))).thenReturn(appointmentDto);
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
                return new JsonPrimitive(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").format(localDateTime));
            }
        }).create();
        String jsonContent = gson.toJson(appointmentDto);

        //When&Then
        mockMvc.perform(post("/v1/clinic/doctors/2/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldEditAppointment() throws Exception {
        //Given
        Gson gson = new Gson();
        AppointmentDto appointmentDto = new AppointmentDto.AppointmentDtoBuilder()
                .id(1L)
                .doctorId(1L)
                .patientId(3L)
                .visitDate(LocalDateTime.of(2019, 11, 5, 8, 0))
                .build();
        when(appointmentFacade.changeAppointmentDate(2L, 1L, LocalDateTime.of(2019, 11, 14, 9, 0))).thenReturn(appointmentDto);
        String jsonContent = gson.toJson(appointmentDto);

        //When&Then
        mockMvc.perform(put("/v1/clinic/doctors/2/appointments/1")
                .param("newTime", "2019-11-14T09:00")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldGetPatientAppointments() throws Exception {
        //Given
        AppointmentDto appointmentDto = new AppointmentDto.AppointmentDtoBuilder()
                .id(1L)
                .doctorId(1L)
                .patientId(3L)
                .visitDate(LocalDateTime.of(2019, 11, 5, 8, 0))
                .build();
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        appointmentDtos.add(appointmentDto);
        when(appointmentFacade.getPatientAppointments(3L)).thenReturn(appointmentDtos);

        //When&Then
        mockMvc.perform(get("/v1/clinic/patients/3/appointments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void shouldRemoveAppointment() throws Exception {
        //Given

        //When&Then
        mockMvc.perform(delete("/v1/clinic/patients/3/appointments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}