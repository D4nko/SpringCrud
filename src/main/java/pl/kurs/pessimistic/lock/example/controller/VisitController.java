package pl.kurs.pessimistic.lock.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.pessimistic.lock.example.model.command.CreateVisitCommand;
import pl.kurs.pessimistic.lock.example.service.VisitService;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;


    @PostMapping
    public ResponseEntity<Void> createVisit(@RequestBody CreateVisitCommand command) {
        try {
            visitService.createVisit(command);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
