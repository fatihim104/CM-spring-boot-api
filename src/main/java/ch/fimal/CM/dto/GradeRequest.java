package ch.fimal.CM.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record GradeRequest(
    @NotNull(message = "Point can not be empty") @DecimalMin(value = "0.01", message = "Point must be greater than 0") @DecimalMax(value = "100.0", message = "Point must be less than 100") Double point) {
}
