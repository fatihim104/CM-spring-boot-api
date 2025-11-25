package ch.fimal.CM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import ch.fimal.CM.exception.CourseNotFoundException;
import ch.fimal.CM.exception.ErrorResponse;
import ch.fimal.CM.exception.GradeNotFoundException;
import ch.fimal.CM.exception.ParticipantNotEnrolledException;
import ch.fimal.CM.exception.ParticipantNotFoundException;
import ch.fimal.CM.model.CourseStatus;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CourseNotFoundException.class, 
                       ParticipantNotFoundException.class,
                       GradeNotFoundException.class,
                       ParticipantNotEnrolledException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException ex) {

        ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList("Cannot delete non-existing resource"));  
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList("Data Integrity Violation: we cannot process your request."));  
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

   
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = "Malformed JSON request";
        Throwable cause = ex.getCause();
        if (cause != null && cause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) cause;
            if (invalidFormatException.getTargetType() != null && invalidFormatException.getTargetType().isAssignableFrom(CourseStatus.class)) {
               
                String allowedStatuses = Arrays.stream(CourseStatus.values())
                                               .map(Enum::name)
                                               .collect(Collectors.joining(", "));

                errorMessage = String.format(
                    "Invalid value for enum %s. Allowed values: [%s]",
                    invalidFormatException.getValue(), 
                    allowedStatuses
                );
            } else {
                errorMessage = String.format("Invalid value: '%s'. Excepted: %s", 
                                             invalidFormatException.getValue(), 
                                             invalidFormatException.getTargetType().getSimpleName());
            }
        }
        ErrorResponse errors = new ErrorResponse(Arrays.asList(errorMessage));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
}
