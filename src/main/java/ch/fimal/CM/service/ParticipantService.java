package ch.fimal.CM.service;

import java.util.List;

import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantRequest;
import ch.fimal.CM.dto.ParticipantResponse;
import ch.fimal.CM.model.Participant;

public interface ParticipantService {
    ParticipantResponse getById(Long id);

    ParticipantResponse save(ParticipantRequest participantRequest);

    void delete(Long id);

    List<ParticipantResponse> getAll();

    ParticipantResponse update(Long id, ParticipantRequest participantRequest);

    List<CourseResponse> getEnrolledCourses(Long id);

    Participant getParticipantEntity(Long id);
}
