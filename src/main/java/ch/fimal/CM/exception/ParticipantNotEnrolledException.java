package ch.fimal.CM.exception;


public class ParticipantNotEnrolledException extends RuntimeException{
    public ParticipantNotEnrolledException(Long participantId, Long courseId) {
        super("Participant with id: " + participantId + " is not enrolled in the course with id: " + courseId);
    }
}
