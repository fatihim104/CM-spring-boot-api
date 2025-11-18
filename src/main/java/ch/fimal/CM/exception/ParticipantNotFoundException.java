package ch.fimal.CM.exception;

public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException(Long id) {
        super("Participant not found with id: " + id);
    }
}
