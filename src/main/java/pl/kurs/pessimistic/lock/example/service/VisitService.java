package pl.kurs.pessimistic.lock.example.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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


    public void createVisit(CreateVisitCommand command) {
//        Doctor doctor = doctorRepository.findById(command.getDoctorId()).orElseThrow();
        Doctor doctor = doctorRepository.findByIdWithLocking(command.getDoctorId()).orElseThrow();
        LocalDateTime date = LocalDateTime.parse(command.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        checkIfVisitExistsAtGivenDate(doctor, date);
        visitRepository.save(new Visit(date, doctor));
    }

    private void checkIfVisitExistsAtGivenDate(Doctor doctor, LocalDateTime date){
        if (doctor.getVisits().stream().anyMatch(v -> v.getDate().equals(date))) {
            throw new IllegalStateException("Visit already exists");
        }
    }
}
