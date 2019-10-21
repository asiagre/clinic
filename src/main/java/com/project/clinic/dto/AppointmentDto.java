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

    public static class AppointmentDtoBuilder {
        private Long id;
        private Long doctorId;
        private Long patientId;
        private LocalDateTime visitDate;

        public AppointmentDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AppointmentDtoBuilder doctorId(Long doctorId) {
            this.doctorId = doctorId;
            return this;
        }

        public AppointmentDtoBuilder patientId(Long patientId) {
            this.patientId = patientId;
            return this;
        }

        public AppointmentDtoBuilder visitDate(LocalDateTime visitDate) {
            this.visitDate = visitDate;
            return this;
        }

        public AppointmentDto build() {
            return new AppointmentDto(id, doctorId, patientId, visitDate);
        }
    }
}
