package ch.fimal.CM.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "instructor")
@Data
public class Instructor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "first_name", nullable = false)
  @NotBlank(message = "First name cannot be empty")
  @Size(min = 3, message = "First name must be at least 3 characters long")
  private String firstName;

  @Column(name = "last_name", nullable = false)
  @NotBlank(message = "Last name cannot be empty")
  @Size(min = 3, message = "Last name must be at least 3 characters long")
  private String lastName;

  @Column(name = "branch", nullable = false)
  @NotBlank(message = "Branch cannot be empty")
  private String branch;

  @Column(name = "email", nullable = false)
  @NotBlank(message = "Email cannot be empty")
  @Email(message = "Email must be valid")
  private String email;

  @Column(name = "start_date")
  @Past(message = "Start date must be in the past")
  private LocalDate startDate;

  @Column(name = "nationality")
  private String nationality;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
  @SQLRestriction("status != 'CourseStatus.PASSIVE'")
  private List<Course> activeCourses;

  @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
  @SQLRestriction("status = 'CourseStatus.PASSIVE'")
  private List<Course> passivCourses;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

}
