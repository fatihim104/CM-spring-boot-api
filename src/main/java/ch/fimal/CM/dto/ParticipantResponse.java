package ch.fimal.CM.dto;

import java.time.LocalDate;
import java.util.Set;

public record ParticipantResponse(
                Long id,
                String firstName,
                String lastName,
                String email,
                LocalDate birthDate,
                Set<CourseResponse> courses) {
}
