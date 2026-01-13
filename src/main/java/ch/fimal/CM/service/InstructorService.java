package ch.fimal.CM.service;

import java.util.List;

import ch.fimal.CM.dto.InstructorRequest;
import ch.fimal.CM.dto.InstructorResponse;
import ch.fimal.CM.model.Instructor;

public interface InstructorService {
    List<InstructorResponse> getAll();
    InstructorResponse getById(Long id);
    InstructorResponse save(InstructorRequest instructorRequest);
    void delete(Long id);
    InstructorResponse update(Long id, InstructorRequest instructorRequest);
    Instructor getInstructorEntity(Long id);
}
