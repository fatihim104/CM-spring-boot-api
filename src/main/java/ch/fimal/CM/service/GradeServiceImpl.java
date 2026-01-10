package ch.fimal.CM.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.fimal.CM.dto.GradeRequest;
import ch.fimal.CM.dto.GradeResponse;
import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.exception.ParticipantNotEnrolledException;
import ch.fimal.CM.mapper.GradeMapper;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.Grade;
import ch.fimal.CM.model.Participant;
import ch.fimal.CM.repository.CourseRepository;
import ch.fimal.CM.repository.GradeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final ParticipantService participantService;
    private final CourseRepository courseRepository;
    private final GradeMapper gradeMapper;

    @Override
    public List<GradeResponse> getAll() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeResponse> getByParticipantId(Long participantId) {
        return gradeRepository.findByParticipantId(participantId).stream()
                .map(gradeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GradeResponse save(GradeRequest gradeRequest, Long participantId, Long courseId) {
        Grade grade = gradeMapper.toEntity(gradeRequest);
        ValidationResult validationResult = validateEnrollment(participantId, courseId);

        grade.setParticipant(validationResult.participant());
        grade.setCourse(validationResult.course());

        Grade savedGrade = gradeRepository.save(grade);
        return gradeMapper.toResponse(savedGrade);
    }

    @Override
    public List<GradeResponse> getByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException(courseId, Course.class);
        }
        return gradeRepository.findByCourseId(courseId).stream()
                .map(gradeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeResponse> getGrade(Long participantId, Long courseId) {
        validateEnrollment(participantId, courseId);
        return gradeRepository.findByParticipantIdAndCourseId(participantId, courseId).stream()
                .map(gradeMapper::toResponse)
                .collect(Collectors.toList());
    }

    private ValidationResult validateEnrollment(Long participantId, Long courseId) {
        Participant participant = participantService.getParticipantEntity(participantId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(courseId, Course.class));

        boolean isEnrolled = participant.getCourses().stream()
                .anyMatch(c -> c.getId().equals(course.getId()));

        if (!isEnrolled)
            throw new ParticipantNotEnrolledException(participantId, courseId);

        return new ValidationResult(participant, course);
    }

    private record ValidationResult(Participant participant, Course course) {
    }

    @Override
    public void deleteById(Long gradeId) {
        if (!gradeRepository.existsById(gradeId)) {
            throw new EntityNotFoundException(gradeId, Grade.class);
        }
        gradeRepository.deleteById(gradeId);
    }

    @Override
    public GradeResponse update(Long gradeId, GradeRequest gradeRequest) {
        Optional<Grade> existingGrade = gradeRepository.findById(gradeId);
        if (existingGrade.isPresent()) {
            Grade unwrappedGrade = existingGrade.get();
            gradeMapper.updateEntityFromRequest(unwrappedGrade, gradeRequest);
            Grade savedGrade = gradeRepository.save(unwrappedGrade);
            return gradeMapper.toResponse(savedGrade);
        } else {
            throw new EntityNotFoundException(gradeId, Grade.class);
        }
    }

}
