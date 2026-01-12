package ch.fimal.CM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fimal.CM.model.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    
}
