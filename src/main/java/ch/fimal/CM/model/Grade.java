package ch.fimal.CM.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grade")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "point")
    @NotNull(message = "Point can not be empty")
    @DecimalMin("0.01")
    @DecimalMax("100.0")
    double point;

    @ManyToOne(optional = false)
    @JoinColumn(name = "participant_id", referencedColumnName = "id")
    private Participant participant;

    @ManyToOne(optional = true)
    @JoinColumn(name= "course_id", referencedColumnName = "id")
    private Course course;
    
}
