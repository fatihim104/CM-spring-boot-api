package ch.fimal.CM.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ch.fimal.CM.dto.CourseRequest;
import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantResponse;
import ch.fimal.CM.model.Course;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    private final ParticipantMapper participantMapper;

    public Course toEntity(CourseRequest request) {
        Course course = new Course();
        course.setName(request.name());
        course.setPlace(request.place());
        course.setStartDate(request.startDate());
        course.setStatus(request.status());
        return course;
    }

    public CourseResponse toResponse(Course course) {
        Set<ParticipantResponse> participantResponses = Collections.emptySet();
        if (course.getParticipants() != null) {
            participantResponses = course.getParticipants().stream()
                    .map(participantMapper::toSummaryResponse)
                    .collect(Collectors.toSet());
        }

        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getPlace(),
                course.getStartDate(),
                course.getStatus(),
                course.getCreatedAt(),
                participantResponses);
    }

    public CourseResponse toSummaryResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getPlace(),
                course.getStartDate(),
                course.getStatus(),
                course.getCreatedAt(),
                Collections.emptySet());
    }

    public void updateEntityFromRequest(Course course, CourseRequest request) {
        course.setName(request.name());
        course.setPlace(request.place());
        course.setStartDate(request.startDate());
        course.setStatus(request.status());
    }
}
