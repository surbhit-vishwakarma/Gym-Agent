package dev.surbhit.gym.agent.repository;

import dev.surbhit.gym.agent.model.db.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {
}
