package ch.fimal.CM.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ch.fimal.CM.dto.CourseRequest;
import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantDto;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Participant;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequest request) {
        Course course = new Course();
        course.setName(request.name());
        course.setPlace(request.place());
        course.setStartDate(request.startDate());
        course.setStatus(request.status());
        return course;
    }

    public CourseResponse toResponse(Course course) {
        Set<ParticipantDto> participantDtos = Collections.emptySet();
        if (course.getParticipants() != null) {
            participantDtos = course.getParticipants().stream()
                    .map(this::toParticipantDto)
                    .collect(Collectors.toSet());
        }

        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getPlace(),
                course.getStartDate(),
                course.getStatus(),
                course.getCreatedAt(),
                participantDtos);
    }

    public ParticipantDto toParticipantDto(Participant participant) {
        return new ParticipantDto(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getEmail(),
                participant.getBirthDate());
    }

    public void updateEntityFromRequest(Course course, CourseRequest request) {
        course.setName(request.name());
        course.setPlace(request.place());
        course.setStartDate(request.startDate());
        course.setStatus(request.status());
    }
}
