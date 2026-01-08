package ch.fimal.CM.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.exception.ParticipantNotEnrolledException;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Grade;
import ch.fimal.CM.model.Participant;
import ch.fimal.CM.repository.GradeRepository;
import ch.fimal.CM.repository.CourseRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final ParticipantService participantService;
    private final CourseRepository courseRepository;

    @Override
    public List<Grade> getAll() {
        return gradeRepository.findAll();
    }

    @Override
    public List<Grade> getByParticipantId(Long student_id) {
        return gradeRepository.findByParticipantId(student_id);
    }

    @Override
    public Grade save(Grade grade, Long participantId, Long courseId) {
        Participant participant = participantService.getParticipantEntity(participantId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(courseId, Course.class));

        boolean isEnrolled = participant.getCourses().stream()
                .anyMatch(c -> c.getId().equals(course.getId()));

        if (!isEnrolled)
            throw new ParticipantNotEnrolledException(participantId, courseId);
        grade.setParticipant(participant);
        grade.setCourse(course);

        return gradeRepository.save(grade);
    }

    @Override
    public List<Grade> getByCourseId(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public List<Grade> getGrade(Long participantId, Long courseId) {
        return gradeRepository.findByParticipantIdAndCourseId(participantId, courseId);
    }

    @Override
    public void deleteById(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    @Override
    public Grade update(Long gradeId, Grade grade) {
        Optional<Grade> existingGrade = gradeRepository.findById(gradeId);
        if (existingGrade.isPresent()) {
            Grade unwrappedGrade = existingGrade.get();
            unwrappedGrade.setPoint(grade.getPoint());
            return gradeRepository.save(unwrappedGrade);
        } else {
            throw new EntityNotFoundException(gradeId, Grade.class);
        }
    }

}
