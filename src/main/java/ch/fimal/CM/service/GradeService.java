package ch.fimal.CM.service;

import java.util.List;

import ch.fimal.CM.model.Grade;

public interface GradeService {
     List<Grade> getAll();
     List<Grade> getByParticipantId(Long participantId);
     List<Grade> getByCourseId(Long courseId);
     Grade save(Grade grade, Long participantId, Long courseId);
     List<Grade> getGrade(Long participantId, Long courseId);
     void deleteById(Long gradeId);
     Grade update( Long gradeId, Grade grade);
}
