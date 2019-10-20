package com.project.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
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
    private Set<Appointment> appointmentList;

    public Patient(Long id, String firstname, String lastname, String pin, String phoneNumber, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Patient(String firstname, String lastname, String pin, String phoneNumber, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public static class PatientBuilder {
        private Long id;
        private String firstname;
        private String lastname;
        private String pin;
        private String phoneNumber;
        private String email;

        public PatientBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PatientBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public PatientBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public PatientBuilder pin(String pin) {
            this.pin = pin;
            return this;
        }

        public PatientBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PatientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Patient build() {
            return new Patient(id, firstname, lastname, pin, phoneNumber, email);
        }
    }
}
