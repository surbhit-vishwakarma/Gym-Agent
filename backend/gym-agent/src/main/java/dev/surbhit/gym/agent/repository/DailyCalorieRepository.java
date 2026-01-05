package dev.surbhit.gym.agent.repository;

import dev.surbhit.gym.agent.model.db.DailyCalorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DailyCalorieRepository extends JpaRepository<DailyCalorie, UUID> {
    Optional<DailyCalorie> findByUser_UserIdAndDate(
            UUID userId,
            LocalDate date
    );

    List<DailyCalorie> findByUser_UserId(UUID userId);
}
