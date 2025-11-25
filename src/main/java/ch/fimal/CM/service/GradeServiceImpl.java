package ch.fimal.CM.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.GradeNotFoundException;
import ch.fimal.CM.exception.ParticipantNotEnrolledException;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Grade;
import ch.fimal.CM.model.Participant;
import ch.fimal.CM.repository.GradeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;
    private final ParticipantService participantService;
    private final CourseService courseService;

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
        Participant participant = participantService.getById(participantId);
        Course course = courseService.getById(courseId);

        if (!participant.getCourses().contains(course)) throw new ParticipantNotEnrolledException(participantId, courseId);
        grade.setParticipant(participant);
        grade.setCourse(course);

        return gradeRepository.save(grade);
    }



    @Override
    public List<Grade> getByCourseId(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public List<Grade> getGrade( Long participantId, Long courseId) {
       return gradeRepository.findByParticipantIdAndCourseId(participantId, courseId);
    }

    @Override
    public void deleteById(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    @Override
    public Grade update(Long gradeId, Grade grade) {
       Optional<Grade> existingGrade = gradeRepository.findById(gradeId);
       if(existingGrade.isPresent()){
        Grade unwrappedGrade = existingGrade.get();
        unwrappedGrade.setPoint(grade.getPoint());
        return gradeRepository.save(unwrappedGrade);
       } else {
        throw new GradeNotFoundException(gradeId);
       }
    }
        

    
}
