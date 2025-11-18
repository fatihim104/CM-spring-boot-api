package ch.fimal.CM.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.CourseNotFoundException;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    
    
    private final CourseRepository courseRepository;
    
    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id)
        .orElseThrow(() -> new CourseNotFoundException(id));
    }
    
    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }
    
    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course update(Long id, Course updatedCourse) {
        return courseRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedCourse.getName());
                    existing.setPlace(updatedCourse.getPlace());
                    existing.setStartDate(updatedCourse.getStartDate());
                    existing.setStatus(updatedCourse.getStatus());
                    return courseRepository.save(existing);
                })
                .orElseThrow(() -> new CourseNotFoundException(id));
    }
}
