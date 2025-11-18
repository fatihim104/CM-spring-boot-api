package ch.fimal.CM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fimal.CM.model.Course;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}