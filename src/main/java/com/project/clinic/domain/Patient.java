package com.project.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NamedNativeQuery(
        name = "Patient.retrievePatientsWhereLastnameFragmentIs",
        query = "SELECT * FROM patients WHERE lower(lastname) LIKE CONCAT('%', :LASTNAME, '%')",
        resultClass = Patient.class
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "pin")
    private String pin;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            targetEntity = Appointment.class,
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Appointment> appointmentList = new HashSet<>();

    public Patient(String firstname, String lastname, String pin, String phoneNumber, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Patient(Long id, String firstname, String lastname, String pin, String phoneNumber, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}
