package ch.fimal.CM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponse {
  private Long id;
  private Double point;
  private Long participantId;
  private String participantName;
  private Long courseId;
  private String courseName;
}
