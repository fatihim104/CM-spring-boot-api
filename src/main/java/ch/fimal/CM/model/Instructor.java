package ch.fimal.CM.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;
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
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "instructor")
@Data
@NoArgsConstructor
@lombok.AllArgsConstructor
public class Instructor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @jakarta.persistence.OneToOne(cascade = CascadeType.ALL)
  @jakarta.persistence.JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public Instructor(String firstName, String lastName, String email, String password, String branch) {
    this.user = new User();
    this.user.setFirstName(firstName);
    this.user.setLastName(lastName);
    this.user.setEmail(email);
    this.user.setPassword(password);
    this.branch = branch;
  }

  public String getFirstName() {
    return user != null ? user.getFirstName() : null;
  }

  public void setFirstName(String firstName) {
    if (user == null)
      user = new User();
    user.setFirstName(firstName);
  }

  public String getLastName() {
    return user != null ? user.getLastName() : null;
  }

  public void setLastName(String lastName) {
    if (user == null)
      user = new User();
    user.setLastName(lastName);
  }

  public String getEmail() {
    return user != null ? user.getEmail() : null;
  }

  public void setEmail(String email) {
    if (user == null)
      user = new User();
    user.setEmail(email);
  }

  @Column(name = "branch", nullable = false)
  @NotBlank(message = "Branch cannot be empty")
  private String branch;

  /*
   * @Column(name = "first_name", nullable = false)
   * 
   * @NotBlank(message = "First name cannot be empty")
   * 
   * @Size(min = 3, message = "First name must be at least 3 characters long")
   * private String firstName;
   * 
   * @Column(name = "last_name", nullable = false)
   * 
   * @NotBlank(message = "Last name cannot be empty")
   * 
   * @Size(min = 3, message = "Last name must be at least 3 characters long")
   * private String lastName;
   * 
   * @Column(name = "email", nullable = false)
   * 
   * @NotBlank(message = "Email cannot be empty")
   * 
   * @Email(message = "Email must be valid")
   * private String email;
   */

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "nationality")
  private String nationality;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
  @SQLRestriction("status != 'FINISHED'")
  private List<Course> activeCourses;

  @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
  @SQLRestriction("status = 'FINISHED'")
  private List<Course> passivCourses;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

}
