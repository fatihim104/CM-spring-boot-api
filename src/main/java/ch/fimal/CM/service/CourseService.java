package ch.fimal.CM.service;

import java.util.List;
import java.util.Set;

import ch.fimal.CM.dto.CourseRequest;
import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantResponse;

public interface CourseService {

    CourseResponse getById(Long id);

    CourseResponse save(CourseRequest courseRequest);

    void delete(Long id);

    List<CourseResponse> getAll();

    CourseResponse update(Long id, CourseRequest courseRequest);

    CourseResponse addParticipantToCourse(Long courseId, Long studenId);

    Set<ParticipantResponse> getEnrolledParticipants(Long id);
}
