package ch.fimal.CM.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record ParticipantRequest(
    @NotBlank(message = "First name cannot be empty") @Size(min = 3, message = "First name is too short") String firstName,

    @NotBlank(message = "Last name cannot be empty") @Size(min = 3, message = "Last name is too short") String lastName,

    int phone,

    @Email(message = "Invalid Email") String email,

    @Past(message = "Date of birth must be in the past") LocalDate birthDate) {
}
