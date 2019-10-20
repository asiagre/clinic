package com.project.clinic.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private Long id;

    private Long doctorId;
    private Long patientId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime visitDate;

    public AppointmentDto(Long id, Long doctorId, Long patientId) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }
}
