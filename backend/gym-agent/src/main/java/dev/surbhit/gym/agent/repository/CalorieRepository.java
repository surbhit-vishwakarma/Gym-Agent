package dev.surbhit.gym.agent.repository;

import dev.surbhit.gym.agent.model.db.AppUser;
import dev.surbhit.gym.agent.model.db.Calorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CalorieRepository extends JpaRepository<Calorie, UUID> {
    Optional<Calorie> findByAppUser_UserId(UUID userId);
}
