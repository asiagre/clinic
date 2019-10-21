package com.project.clinic.scheduler;

import com.project.clinic.config.AdminConfig;
import com.project.clinic.domain.Appointment;
import com.project.clinic.mail.Mail;
import com.project.clinic.mail.SimpleEmailService;
import com.project.clinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailScheduler {

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendReminderEmail() {
        List<Appointment> appointments = getAppointments();
        appointments.forEach(appointment -> emailSender(appointment));
    }

    private void emailSender(Appointment appointment) {
        emailService.send(new Mail(
                adminConfig.getAdminMail(),
                "Appointment reminder",
                createMessage(appointment))
        );
    }

    private List<Appointment> getAppointments() {
        LocalDateTime today = LocalDateTime.now();
        return appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getVisitDate().getYear() == today.getYear())
                .filter(appointment -> appointment.getVisitDate().getDayOfYear() == (today.getDayOfYear() + 1))
                .collect(Collectors.toList());

    }

    private String createMessage(Appointment appointment) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
        return "Hello, Mrs/Mr. " + appointment.getPatient().getLastname() +  ",\n\nwe would like to remind you that " +
                appointment.getVisitDate().format(dateFormatter) + " at " +
                appointment.getVisitDate().format(timeFormatter) + " you have scheduled appointment with dr " +
                appointment.getDoctor().getLastname() + ". \nIf you want cancel visit please call us or visit our website.\n" +
                "\nBest regards,\nNEOCLINIC";
    }
}
