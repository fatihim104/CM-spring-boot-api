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
import org.springframework.web.bind.annotation.RestController;

import ch.fimal.CM.dto.GradeRequest;
import ch.fimal.CM.dto.GradeResponse;
import ch.fimal.CM.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<GradeResponse>> getAll() {
        return new ResponseEntity<>(gradeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<GradeResponse>> getParticipantsGrade(@PathVariable Long participantId) {
        return new ResponseEntity<>(gradeService.getByParticipantId(participantId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<GradeResponse>> getCouresGrade(@PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/participant/{participantId}/course/{courseId}")
    public ResponseEntity<List<GradeResponse>> getGrade(@PathVariable Long participantId,
            @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getGrade(participantId, courseId), HttpStatus.OK);
    }

    @PostMapping("/participant/{participantId}/course/{courseId}")
    public ResponseEntity<GradeResponse> save(@Valid @RequestBody GradeRequest gradeRequest,
            @PathVariable Long participantId, @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.save(gradeRequest, participantId, courseId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long gradeId) {
        gradeService.deleteById(gradeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{gradeId}")
    public ResponseEntity<GradeResponse> update(@Valid @RequestBody GradeRequest gradeRequest,
            @PathVariable Long gradeId) {
        return new ResponseEntity<>(gradeService.update(gradeId, gradeRequest), HttpStatus.OK);
    }
}
