package ch.fimal.CM.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "participant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 3, message = "First name is too short")
    private String firstName;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 3, message = "Last name is too short")
    private String lastName;

    @Column(nullable = true, length = 16)
    private int phone;

    @Email(message = "Invalid Email")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = true)
    private LocalDate birthDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    
}
