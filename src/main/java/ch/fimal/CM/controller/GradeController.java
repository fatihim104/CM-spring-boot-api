package ch.fimal.CM.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fimal.CM.model.Grade;
import ch.fimal.CM.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<Grade>> getAll() {
        return new ResponseEntity<>(gradeService.getAll(), HttpStatus.OK);        
    }

    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<Grade>> getParticipantsGrade(@PathVariable Long participantId) {
        return new ResponseEntity<>(gradeService.getByParticipantId(participantId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Grade>> getCouresGrade(@PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/participant/{participantId}/course/{courseId}")
    public ResponseEntity<List<Grade>> getGrade(@PathVariable Long participantId,
                                                @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getGrade( participantId, courseId), HttpStatus.OK);
    }

    @PostMapping("/participant/{participantId}/course/{courseId}") 
    public ResponseEntity<Grade> save(@Valid @RequestBody Grade grade, @PathVariable Long participantId, @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.save(grade, participantId, courseId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long gradeId) {
        gradeService.deleteById(gradeId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{gradeId}")
    public ResponseEntity<Grade> update(@RequestBody Grade grade, @PathVariable Long gradeId){
        return new ResponseEntity<>(gradeService.update(gradeId, grade), HttpStatus.OK);
    }
}
