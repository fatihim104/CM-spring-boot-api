package ch.fimal.CM.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.fimal.CM.dto.CourseResponse;
import ch.fimal.CM.dto.ParticipantRequest;
import ch.fimal.CM.dto.ParticipantResponse;
import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.mapper.CourseMapper;
import ch.fimal.CM.mapper.ParticipantMapper;
import ch.fimal.CM.model.Participant;
import ch.fimal.CM.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;
    private final CourseMapper courseMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ParticipantResponse getById(Long id) {
        return participantRepository.findById(id)
                .map(participantMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(id, Participant.class));
    }

    @Override
    public ParticipantResponse save(ParticipantRequest participantRequest) {
        Participant participant = participantMapper.toEntity(participantRequest);
        participant.getUser().setPassword(bCryptPasswordEncoder.encode(participant.getUser().getPassword()));
        return participantMapper.toResponse(participantRepository.save(participant));
    }

    @Override
    public void delete(Long id) {
        participantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Participant.class));
        participantRepository.deleteById(id);
    }

    @Override
    public List<ParticipantResponse> getAll() {
        return participantRepository.findAll().stream()
                .map(participantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantResponse update(Long id, ParticipantRequest participantRequest) {
        Participant existingParticipant = participantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Participant.class));
        participantMapper.updateEntityFromRequest(existingParticipant, participantRequest);
        return participantMapper.toResponse(participantRepository.save(existingParticipant));
    }

    @Override
    public List<CourseResponse> getEnrolledCourses(Long id) {
        Participant participant = getParticipantEntity(id);

        if (participant.getCourses() == null) {
            return Collections.emptyList();
        }
        return participant.getCourses().stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Participant getParticipantEntity(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Participant.class));
    }

}
