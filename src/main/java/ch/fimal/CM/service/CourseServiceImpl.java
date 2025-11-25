package ch.fimal.CM.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.CourseNotFoundException;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Participant;
import ch.fimal.CM.repository.CourseRepository;
import ch.fimal.CM.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    
    
    private final CourseRepository courseRepository;
    private final ParticipantService participantService;
    
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

    @Override
    public Course addParticipantToCourse(Long courseId, Long participantId) {
        Course course = getById(courseId);
        Participant participant = participantService.getById(participantId);
        course.getParticipants().add(participant);
        return save(course);
    }

    @Override
    public Set<Participant> getEnrolledParticipants(Long id) {
       Course course = getById(id);
       return course.getParticipants();
    }
}
