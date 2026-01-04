package ch.fimal.CM.dto;

import java.time.LocalDate;

import ch.fimal.CM.model.CourseStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotBlank(message = "Name cannot be empty") @Size(min = 3, message = "Name is too short") String name,

        @NotBlank(message = "Place cannot be empty") @Size(min = 3, message = "Place is too short") String place,

        @NotNull(message = "Date cannot be null") @Future(message = "Date must be in the future") LocalDate startDate,

        @NotNull(message = "Status cannot be null") CourseStatus status) {
}
