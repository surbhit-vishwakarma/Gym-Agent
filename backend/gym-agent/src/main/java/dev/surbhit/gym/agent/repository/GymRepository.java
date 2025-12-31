package dev.surbhit.gym.agent.repository;

import dev.surbhit.gym.agent.model.db.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GymRepository extends JpaRepository<Gym, UUID> {

    List<Gym> findAllByOwnerId(UUID ownerId);
}
