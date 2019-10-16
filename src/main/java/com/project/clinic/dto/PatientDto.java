package com.project.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String pin;
    private String phoneNumber;
    private String email;

    public static class PatientDtoBuilder {
        private Long id;
        private String firstname;
        private String lastname;
        private String pin;
        private String phoneNumber;
        private String email;

        public PatientDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PatientDtoBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public PatientDtoBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public PatientDtoBuilder pin(String pin) {
            this.pin = pin;
            return this;
        }

        public PatientDtoBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PatientDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PatientDto build() {
            return new PatientDto(id, firstname, lastname, pin, phoneNumber, email);
        }
    }
}
