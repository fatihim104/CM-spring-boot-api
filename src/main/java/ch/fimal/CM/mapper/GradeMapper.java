package ch.fimal.CM.mapper;

import org.springframework.stereotype.Component;

import ch.fimal.CM.dto.GradeRequest;
import ch.fimal.CM.dto.GradeResponse;
import ch.fimal.CM.model.Grade;

@Component
public class GradeMapper {

  public Grade toEntity(GradeRequest request) {
    Grade grade = new Grade();
    grade.setPoint(request.point());
    return grade;
  }

  public GradeResponse toResponse(Grade grade) {
    return new GradeResponse(
        grade.getId(),
        grade.getPoint(),
        grade.getParticipant() != null ? grade.getParticipant().getId() : null,
        grade.getParticipant() != null
            ? grade.getParticipant().getFirstName() + " " + grade.getParticipant().getLastName()
            : null,
        grade.getCourse() != null ? grade.getCourse().getId() : null,
        grade.getCourse() != null ? grade.getCourse().getName() : null);
  }

  public void updateEntityFromRequest(Grade grade, GradeRequest request) {
    grade.setPoint(request.point());
  }
}
