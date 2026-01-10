package ch.fimal.CM.service;

import java.util.List;

import ch.fimal.CM.dto.GradeRequest;
import ch.fimal.CM.dto.GradeResponse;

public interface GradeService {
     List<GradeResponse> getAll();

     List<GradeResponse> getByParticipantId(Long participantId);

     List<GradeResponse> getByCourseId(Long courseId);

     GradeResponse save(GradeRequest gradeRequest, Long participantId, Long courseId);

     List<GradeResponse> getGrade(Long participantId, Long courseId);

     void deleteById(Long gradeId);

     GradeResponse update(Long gradeId, GradeRequest gradeRequest);
}
