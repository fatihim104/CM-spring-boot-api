package ch.fimal.CM.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.mapper.CourseMapper;
import ch.fimal.CM.model.Course;
import ch.fimal.CM.model.CourseStatus;
import ch.fimal.CM.repository.CourseRepository;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

  @Mock
  private CourseRepository courseRepository;

  @InjectMocks
  private CourseServiceImpl courseService;

  @Mock
  private CourseMapper courseMapper;

  @Test
  void getCourse_shouldReturnCourse_whenCourseExists() {
    // Given
    Course course = new Course("English", "Zürich", LocalDate.parse(("2026-05-05")), CourseStatus.PLANNING);
    course.setId(1L);

    // Prepare expected response to be returned by the mocked mapper
    CourseResponse expectedResponse = new CourseResponse(
        1L,
        "English",
        "Zürich",
        LocalDate.parse("2026-05-05"),
        CourseStatus.PLANNING,
        null, // createdAt is not set in constructor of Course, usually set in entity
              // lifecycle
        Collections.emptySet(),
        null // instructor
    );

    // When
    when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
    when(courseMapper.toResponse(course)).thenReturn(expectedResponse);

    CourseResponse result = courseService.getById(1L);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.id());
    assertEquals("English", result.name());
    assertEquals("Zürich", result.place());
    assertEquals(LocalDate.parse(("2026-05-05")), result.startDate());
    assertEquals(CourseStatus.PLANNING, result.status());
  }
}
