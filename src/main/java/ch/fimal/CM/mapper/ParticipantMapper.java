package ch.fimal.CM.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ch.fimal.CM.dto.CourseSummary;
import ch.fimal.CM.dto.ParticipantRequest;
import ch.fimal.CM.dto.ParticipantResponse;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Participant;

@Component
public class ParticipantMapper {

  public Participant toEntity(ParticipantRequest request) {
    Participant participant = new Participant();
    participant.setFirstName(request.firstName());
    participant.setLastName(request.lastName());
    participant.setPhone(request.phone());
    participant.setEmail(request.email());
    participant.setBirthDate(request.birthDate());
    return participant;
  }

  public ParticipantResponse toResponse(Participant participant) {
    Set<CourseSummary> courses = Collections.emptySet();

    if (participant.getCourses() != null) {
      courses = participant.getCourses().stream()
          .map(this::toCourseSummary)
          .collect(Collectors.toSet());
    }

    return new ParticipantResponse(
        participant.getId(),
        participant.getFirstName(),
        participant.getLastName(),
        participant.getEmail(),
        participant.getBirthDate(),
        courses);
  }

  public ParticipantResponse toSummaryResponse(Participant participant) {
    return new ParticipantResponse(
        participant.getId(),
        participant.getFirstName(),
        participant.getLastName(),
        participant.getEmail(),
        participant.getBirthDate(),
        Collections.emptySet());
  }

  public void updateEntityFromRequest(Participant participant, ParticipantRequest request) {
    participant.setFirstName(request.firstName());
    participant.setLastName(request.lastName());
    participant.setBirthDate(request.birthDate());
    participant.setPhone(request.phone());
    participant.setEmail(request.email());
  }

  /**
   * Converts Course entity to CourseSummary DTO
   * Returns a lightweight course representation without instructor or
   * participants
   */
  private CourseSummary toCourseSummary(Course course) {
    return new CourseSummary(
        course.getId(),
        course.getName(),
        course.getPlace(),
        course.getStartDate(),
        course.getStatus(),
        course.getCreatedAt());
  }
}
