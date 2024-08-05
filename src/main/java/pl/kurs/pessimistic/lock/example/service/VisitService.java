package pl.kurs.pessimistic.lock.example.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.pessimistic.lock.example.model.Doctor;
import pl.kurs.pessimistic.lock.example.model.Visit;
import pl.kurs.pessimistic.lock.example.model.command.CreateVisitCommand;
import pl.kurs.pessimistic.lock.repository.DoctorRepository;
import pl.kurs.pessimistic.lock.repository.VisitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final DoctorRepository doctorRepository;
    private final VisitRepository visitRepository;


    @PostConstruct
    public void init() {
        doctorRepository.saveAndFlush(new Doctor("Doctor_1"));
        doctorRepository.saveAndFlush(new Doctor("Doctor_2"));
    }

    @Transactional
    public void createVisit(CreateVisitCommand command) {
        try {
            Doctor doctor = doctorRepository.findByIdWithLocking(command.getDoctorId())
                    .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
            LocalDateTime date = LocalDateTime.parse(command.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            checkIfVisitExistsAtGivenDate(doctor, date);
            visitRepository.save(new Visit(date, doctor));
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void clearVisits() {
        visitRepository.deleteAll();
    }

    private void checkIfVisitExistsAtGivenDate(Doctor doctor, LocalDateTime date) {
        if (doctor.getVisits().stream().anyMatch(v -> v.getDate().equals(date))) {
            throw new IllegalStateException("Visit already exists");
        }
    }
}
