package ch.fimal.CM.service;

import java.util.List;

import ch.fimal.CM.model.Instructor;

public interface InstructorService {
    List<Instructor> getAll();
    Instructor getById(Long id);
    Instructor save(Instructor instructor);
    void delete(Long id);
    Instructor update(Long id, Instructor instructor);
}
