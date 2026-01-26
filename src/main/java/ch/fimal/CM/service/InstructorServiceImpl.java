package ch.fimal.CM.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.fimal.CM.dto.InstructorRequest;
import ch.fimal.CM.dto.InstructorResponse;
import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.mapper.InstructorMapper;
import ch.fimal.CM.model.Instructor;
import ch.fimal.CM.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<InstructorResponse> getAll() {
        return instructorRepository.findAll().stream()
                .map(instructorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorResponse getById(Long id) {
        Instructor instructor = getInstructorEntity(id);
        return instructorMapper.toResponse(instructor);
    }

    @Override
    public InstructorResponse save(InstructorRequest instructorRequest) {
        Instructor instructor = instructorMapper.toEntity(instructorRequest);
        instructor.getUser().setPassword(bCryptPasswordEncoder.encode(instructor.getUser().getPassword()));
        Instructor savedInstructor = instructorRepository.save(instructor);
        return instructorMapper.toResponse(savedInstructor);
    }

    @Override
    public void delete(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new EntityNotFoundException(id, Instructor.class);
        }
        instructorRepository.deleteById(id);
    }

    @Override
    public InstructorResponse update(Long id, InstructorRequest instructorRequest) {
        Instructor instructor = getInstructorEntity(id);
        instructorMapper.updateEntityFromRequest(instructor, instructorRequest);
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return instructorMapper.toResponse(updatedInstructor);
    }

    @Override
    public Instructor getInstructorEntity(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Instructor.class));
    }

}
