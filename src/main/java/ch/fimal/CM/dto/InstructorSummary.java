package ch.fimal.CM.dto;

/**
 * Lightweight instructor DTO to prevent circular references
 * Used when instructor info is nested within other responses
 */
public record InstructorSummary(
    Long id,
    String firstName,
    String lastName,
    String branch) {
}
