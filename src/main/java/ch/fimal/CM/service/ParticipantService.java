package ch.fimal.CM.service;

import java.util.List;

import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Participant;

public interface ParticipantService {
    Participant getById(Long id);
    Participant save(Participant participant);
    void delete(Long id);
    List<Participant> getAll();
    Participant update(Long id, Participant participant);
    List<Course> getEnrolledCourses(Long id);
}
