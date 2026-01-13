package ch.fimal.CM.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InstructorRequest(
                @NotBlank(message = "First name cannot be empty") @Size(min = 3, message = "First name must be at least 3 characters long") String firstName,
                @NotBlank(message = "Last name cannot be empty") @Size(min = 3, message = "Last name must be at least 3 characters long") String lastName,
                @NotBlank(message = "Branch cannot be empty") String branch,
                @NotBlank(message = "Email cannot be empty") @Email(message = "Email must be valid") String email,
                LocalDate startDate,
                String nationality,
                Set<CourseResponse> courses) {
}
