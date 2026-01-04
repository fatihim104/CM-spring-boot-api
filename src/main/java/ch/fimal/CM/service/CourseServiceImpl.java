package ch.fimal.CM.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.fimal.CM.dto.CourseRequest;
import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantDto;
import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.mapper.CourseMapper;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Participant;
import ch.fimal.CM.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ParticipantService participantService;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getById(Long id) {
        Course course = getCourseEntity(id);
        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse save(CourseRequest courseRequest) {
        Course course = courseMapper.toEntity(courseRequest);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponse(savedCourse);
    }

    @Override
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException(id, Course.class);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public CourseResponse update(Long id, CourseRequest courseRequest) {
        Course course = getCourseEntity(id);
        courseMapper.updateEntityFromRequest(course, courseRequest);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toResponse(updatedCourse);
    }

    @Override
    public CourseResponse addParticipantToCourse(Long courseId, Long participantId) {
        Course course = getCourseEntity(courseId);
        Participant participant = participantService.getById(participantId);
        course.getParticipants().add(participant);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponse(savedCourse);
    }

    @Override
    public Set<ParticipantDto> getEnrolledParticipants(Long id) {
        Course course = getCourseEntity(id);
        return course.getParticipants().stream()
                .map(courseMapper::toParticipantDto)
                .collect(Collectors.toSet());
    }

    private Course getCourseEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Course.class));
    }
}
