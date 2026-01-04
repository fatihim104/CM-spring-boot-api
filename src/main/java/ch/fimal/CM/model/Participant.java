package ch.fimal.CM.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@RequiredArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 3, message = "First name is too short")
    @NonNull
    private String firstName;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 3, message = "Last name is too short")
    @NonNull
    private String lastName;

    @Column(nullable = true, length = 16)
    private int phone;

    @Email(message = "Invalid Email")
    @NonNull
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = true)
    @NonNull
    private LocalDate birthDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Grade> grades;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "course_participant", joinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private List<Course> courses;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
