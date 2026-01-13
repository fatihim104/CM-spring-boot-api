package ch.fimal.CM.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.InstructorRequest;
import ch.fimal.CM.dto.InstructorResponse;
import ch.fimal.CM.model.Instructor;

@Component
public class InstructorMapper {

    private CourseMapper courseMapper;

    public InstructorMapper(@Lazy CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

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
        Set<CourseResponse> activecourses = Collections.emptySet();
        Set<CourseResponse> passivcourses = Collections.emptySet();

        if (instructor.getActiveCourses() != null) {
            activecourses = instructor.getActiveCourses().stream()
                    .map(courseMapper::toSummaryResponse)
                    .collect(Collectors.toSet());
        }

        if (instructor.getPassivCourses() != null) {
            passivcourses = instructor.getPassivCourses().stream()
                    .map(courseMapper::toSummaryResponse)
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
                activecourses,
                passivcourses);
    }

    public void updateEntityFromRequest(Instructor instructor, InstructorRequest request) {
        instructor.setFirstName(request.firstName());
        instructor.setLastName(request.lastName());
        instructor.setBranch(request.branch());
        instructor.setEmail(request.email());
        instructor.setStartDate(request.startDate());
        instructor.setNationality(request.nationality());
    }
}
