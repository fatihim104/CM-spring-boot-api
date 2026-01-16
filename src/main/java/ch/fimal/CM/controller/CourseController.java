package ch.fimal.CM.controller;

import java.util.List;
import java.util.Set;

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

import ch.fimal.CM.dto.CourseRequest;
import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantResponse;
import ch.fimal.CM.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<CourseResponse>> getAll() {
        return new ResponseEntity<>(courseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseResponse> save(@Valid @RequestBody CourseRequest courseRequest) {
        return new ResponseEntity<>(courseService.save(courseRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        courseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> update(@Valid @PathVariable Long id,
            @Valid @RequestBody CourseRequest courseRequest) {
        return new ResponseEntity<>(courseService.update(id, courseRequest), HttpStatus.OK);
    }

    @PutMapping("/{courseId}/participant/{participantId}")
    public ResponseEntity<CourseResponse> enrollParticipantToCourse(@PathVariable Long courseId,
            @PathVariable Long participantId) {
        return new ResponseEntity<>(courseService.addParticipantToCourse(courseId, participantId), HttpStatus.OK);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<Set<ParticipantResponse>> getEnrolledParticipants(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getEnrolledParticipants(id), HttpStatus.OK);
    }

    @PutMapping("{courseId}/instructor/{instructorId}")
    public ResponseEntity<CourseResponse> assignInstructorToCourse(@PathVariable Long courseId,
            @PathVariable Long instructorId) {
        return new ResponseEntity<>(courseService.assignInstructor(courseId, instructorId), HttpStatus.OK);
    }
}
