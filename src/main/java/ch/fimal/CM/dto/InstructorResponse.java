package ch.fimal.CM.dto;

import java.time.LocalDate;
import java.util.Set;

public record InstructorResponse(
        Long id,
        String firstName,
        String lastName,
        String branch,
        String email,
        LocalDate startDate,
        String nationality,
        Set<CourseSummary> activeCourses,
        Set<CourseSummary> passivCourses) {
}
