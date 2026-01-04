package ch.fimal.CM.dto;

import java.time.LocalDate;

public record ParticipantDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate) {
}
