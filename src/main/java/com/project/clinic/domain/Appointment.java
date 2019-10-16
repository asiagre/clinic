package com.project.clinic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
@EqualsAndHashCode
public class Appointment {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "visit_date")
    //@JsonFormat(pattern = "10/05/2005 08:00")
    private LocalDateTime visitDate;

    public Appointment(Doctor doctor, Patient patient, LocalDateTime visitDate) {
        this.doctor = doctor;
        this.patient = patient;
        this.visitDate = visitDate;
    }
}
