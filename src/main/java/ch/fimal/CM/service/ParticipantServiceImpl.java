package ch.fimal.CM.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.ParticipantNotFoundException;
import ch.fimal.CM.model.Participant;
import ch.fimal.CM.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Override
    public Participant getById(Long id) {
        return participantRepository.findById(id)
        .orElseThrow(() -> new ParticipantNotFoundException(id));
    }

    @Override
    public Participant save(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public void delete(Long id) {
        participantRepository.deleteById(id);
    }

    @Override
    public List<Participant> getAll() {
        return participantRepository.findAll();
    }

    @Override
    public Participant update(Long id, Participant updatedParticipant) {
        return participantRepository.findById(id)
            .map(existing -> {
                existing.setFirstName(updatedParticipant.getFirstName());
                existing.setLastName(updatedParticipant.getLastName());
                existing.setBirthDate(updatedParticipant.getBirthDate());
                existing.setPhone(updatedParticipant.getPhone());
                existing.setEmail(updatedParticipant.getEmail());
                return participantRepository.save(existing);
            })
            .orElseThrow(() ->new ParticipantNotFoundException(id));
    }
    
}
