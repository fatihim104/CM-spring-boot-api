package ch.fimal.CM.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantRequest;
import ch.fimal.CM.dto.ParticipantResponse;
import ch.fimal.CM.model.Participant;
import org.springframework.context.annotation.Lazy;

@Component
public class ParticipantMapper {
  private CourseMapper courseMapper;

  public ParticipantMapper(@Lazy CourseMapper courseMapper) {
    this.courseMapper = courseMapper;
  }

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
    Set<CourseResponse> courses = Collections.emptySet();

    if (participant.getCourses() != null) {
      courses = participant.getCourses().stream()
          .map(courseMapper::toSummaryResponse)
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
}
