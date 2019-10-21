package com.project.clinic.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String specialization;
    private double rating;
    private List<Integer> scores = new ArrayList<>();
    private List<LocalDateTime> freeSlots = new ArrayList<>();

    public static class DoctorDtoBuilder {
        private Long id;
        private String firstname;
        private String lastname;
        private String specialization;
        private double rating;
        private List<Integer> scores;
        private List<LocalDateTime> freeSlots;

        public DoctorDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DoctorDtoBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public DoctorDtoBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public DoctorDtoBuilder specialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        public DoctorDtoBuilder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public DoctorDtoBuilder scores(List<Integer> scores) {
            this.scores = new ArrayList<>();
            scores.forEach(score -> this.scores.add(score));
            return this;
        }

        public DoctorDtoBuilder freeSlots(List<LocalDateTime> slots) {
            this.freeSlots = new ArrayList<>();
            slots.forEach(slot -> this.freeSlots.add(slot));
            return this;
        }

        public DoctorDto build() {
            if(scores == null) {
                this.scores = new ArrayList<>();
            }
            if(freeSlots == null) {
                this.freeSlots = new ArrayList<>();
            }
            return new DoctorDto(id, firstname, lastname, specialization, rating, scores, freeSlots);
        }
    }

}
