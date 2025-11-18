package ch.fimal.CM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.fimal.CM.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {}  
