package ch.fimal.CM.dto;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(
    @NotBlank(message = "Permission name cannot be blank") String name) {
}
