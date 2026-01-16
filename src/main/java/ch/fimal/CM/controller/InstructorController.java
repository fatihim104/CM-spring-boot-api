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

import ch.fimal.CM.dto.InstructorRequest;
import ch.fimal.CM.dto.InstructorResponse;
import ch.fimal.CM.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<InstructorResponse>> getAll() {
        return new ResponseEntity<>(instructorService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<InstructorResponse> save(@Valid @RequestBody InstructorRequest instuctorRequest) {
        return new ResponseEntity<>(instructorService.save(instuctorRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<InstructorResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(instructorService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        instructorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorResponse> update(@PathVariable Long id,
            @Valid @RequestBody InstructorRequest instuctorRequest) {
        return new ResponseEntity<>(instructorService.update(id, instuctorRequest), HttpStatus.OK);
    }

}
