package ch.fimal.CM.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ch.fimal.CM.model.CourseStatus;

/**
 * Lightweight course DTO to prevent circular references
 * Used when course info is nested within other responses (e.g.,
 * InstructorResponse)
 * Does not include instructor or participants to avoid circular dependencies
 */
public record CourseSummary(
    Long id,
    String name,
    String place,
    LocalDate startDate,
    CourseStatus status,
    LocalDateTime createdAt) {
}
