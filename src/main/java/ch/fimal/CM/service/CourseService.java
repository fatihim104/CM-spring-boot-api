package ch.fimal.CM.service;

import java.util.List;
import java.util.Set;

import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Participant;

public interface CourseService {
    
    Course getById(Long id);
    Course save(Course course);
    void delete(Long id);
    List<Course> getAll();
    Course update(Long id, Course course);
    Course addParticipantToCourse(Long courseId, Long studenId);
    Set<Participant> getEnrolledParticipants(Long id);    
}
