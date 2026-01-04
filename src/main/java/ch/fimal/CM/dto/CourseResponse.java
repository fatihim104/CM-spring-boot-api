package ch.fimal.CM.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import ch.fimal.CM.model.CourseStatus;

public record CourseResponse(
        Long id,
        String name,
        String place,
        LocalDate startDate,
        CourseStatus status,
        LocalDateTime createdAt,
        Set<ParticipantDto> participants) {
}
