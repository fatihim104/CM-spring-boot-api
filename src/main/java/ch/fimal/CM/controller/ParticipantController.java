package ch.fimal.CM.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantRequest;
import ch.fimal.CM.dto.ParticipantResponse;
import ch.fimal.CM.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ParticipantResponse>> getAll() {
        return new ResponseEntity<>(participantService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(participantService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ParticipantResponse> save(@Valid @RequestBody ParticipantRequest participantRequest) {
        return new ResponseEntity<>(participantService.save(participantRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        participantService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponse> update(@Valid @PathVariable Long id,
            @Valid @RequestBody ParticipantRequest participantRequest) {
        return new ResponseEntity<>(participantService.update(id, participantRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<List<CourseResponse>> getEnrolledCourses(@PathVariable Long id) {
        return new ResponseEntity<>(participantService.getEnrolledCourses(id), HttpStatus.OK);
    }
}
