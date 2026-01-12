package ch.fimal.CM.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.model.Instructor;
import ch.fimal.CM.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Override
    public List<Instructor> getAll() {
       return instructorRepository.findAll();
    }

    @Override
    public Instructor getById(Long id) {
        return instructorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(id, Instructor.class));
    }

    @Override
    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public void delete(Long id) {
        if(!instructorRepository.existsById(id)){
            throw new EntityNotFoundException(id, Instructor.class);
        }
        instructorRepository.deleteById(id);
    }

    @Override
    public Instructor update(Long id, Instructor instructor) {
       Instructor existingInstructor = getById(id);
        existingInstructor.setFirstName(instructor.getFirstName());
        existingInstructor.setLastName(instructor.getLastName());
        existingInstructor.setBranch(instructor.getBranch());
        existingInstructor.setEmail(instructor.getEmail());
        existingInstructor.setStartDate(instructor.getStartDate());
        existingInstructor.setNationality(instructor.getNationality());

        return instructorRepository.save(existingInstructor);
    }
    
}
