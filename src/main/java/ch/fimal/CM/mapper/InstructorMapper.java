package ch.fimal.CM.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ch.fimal.CM.dto.CourseSummary;
import ch.fimal.CM.dto.InstructorRequest;
import ch.fimal.CM.dto.InstructorResponse;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Instructor;

@Component
public class InstructorMapper {

    public Instructor toEntity(InstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setFirstName(request.firstName());
        instructor.setLastName(request.lastName());
        instructor.setBranch(request.branch());
        instructor.setEmail(request.email());
        instructor.setStartDate(request.startDate());
        instructor.setNationality(request.nationality());
        return instructor;
    }

    public InstructorResponse toResponse(Instructor instructor) {
        Set<CourseSummary> activeCourses = Collections.emptySet();
        Set<CourseSummary> passivCourses = Collections.emptySet();

        if (instructor.getActiveCourses() != null) {
            activeCourses = instructor.getActiveCourses().stream()
                    .map(this::toCourseSummary)
                    .collect(Collectors.toSet());
        }

        if (instructor.getPassivCourses() != null) {
            passivCourses = instructor.getPassivCourses().stream()
                    .map(this::toCourseSummary)
                    .collect(Collectors.toSet());
        }

        return new InstructorResponse(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getBranch(),
                instructor.getEmail(),
                instructor.getStartDate(),
                instructor.getNationality(),
                activeCourses,
                passivCourses);
    }

    public void updateEntityFromRequest(Instructor instructor, InstructorRequest request) {
        instructor.setFirstName(request.firstName());
        instructor.setLastName(request.lastName());
        instructor.setBranch(request.branch());
        instructor.setEmail(request.email());
        instructor.setStartDate(request.startDate());
        instructor.setNationality(request.nationality());
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
