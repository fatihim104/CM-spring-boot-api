package ch.fimal.CM.service;

import java.util.List;

import ch.fimal.CM.model.Course;

public interface CourseService {
    
    Course getById(Long id);
    Course save(Course course);
    void delete(Long id);
    List<Course> getAll();
    Course update(Long id, Course course);
    
}
