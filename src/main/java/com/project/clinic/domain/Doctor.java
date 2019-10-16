package com.project.clinic.domain;

import com.project.clinic.exception.SlotsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedNativeQuery(
        name = "Doctor.retrieveDoctorsWhereLastnameFragmentIs",
        query = "SELECT * FROM doctors WHERE lower(lastname) LIKE CONCAT('%', :LASTNAME, '%')",
        resultClass = Doctor.class
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "rating")
    private double rating;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> scores = new ArrayList<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LocalDateTime> slots = new ArrayList<>();

    public Doctor(String firstname, String lastname, String specialization, double rating) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.specialization = specialization;
        this.rating = rating;
    }

    public static class DoctorBuilder {
        private Long id;
        private String firstname;
        private String lastname;
        private String specialization;
        private double rating;
        private List<Integer> scores;
        private List<LocalDateTime> freeSlots;

        public DoctorBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DoctorBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public DoctorBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public DoctorBuilder specialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        public DoctorBuilder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public DoctorBuilder scores(List<Integer> scores) {
            this.scores = new ArrayList<>();
            scores.forEach(score -> this.scores.add(score));
            return this;
        }

        public DoctorBuilder freeSlots(List<LocalDateTime> slots) {
            this.freeSlots = new ArrayList<>();
            slots.forEach(slot -> this.freeSlots.add(slot));
            return this;
        }

        public Doctor build() {
            return new Doctor(id, firstname, lastname, specialization, rating, scores, freeSlots);
        }
    }
}
