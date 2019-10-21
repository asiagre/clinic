package com.project.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

}
