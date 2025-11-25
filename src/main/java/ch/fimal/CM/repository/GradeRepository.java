package ch.fimal.CM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.fimal.CM.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByParticipantId(Long participantId);

    List<Grade> findByCourseId(Long courseId);

    List<Grade> findByParticipantIdAndCourseId(Long participantId, Long courseId);
    
}
