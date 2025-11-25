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

import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Participant;
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
    public ResponseEntity<List<Course>> getAll(){
        return new ResponseEntity<>(courseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getById(id), HttpStatus.OK);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    }

    @PostMapping
    public ResponseEntity<Course> save(@Valid @RequestBody Course course) {
        return new ResponseEntity<>(courseService.save(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        courseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@Valid @PathVariable Long id, @Valid @RequestBody Course updatedCours) {
        return new ResponseEntity<>(courseService.update(id, updatedCours), HttpStatus.OK);
    }

    @PutMapping("/{courseId}/participant/{participantId}")
    public ResponseEntity<Course> enrollParticipantToCourse(@PathVariable Long courseId, @PathVariable Long participantId) {
        return new ResponseEntity<>(courseService.addParticipantToCourse(courseId, participantId), HttpStatus.OK);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<Set<Participant>> getEnrolledParticipants(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getEnrolledParticipants(id), HttpStatus.OK);
    }
}
