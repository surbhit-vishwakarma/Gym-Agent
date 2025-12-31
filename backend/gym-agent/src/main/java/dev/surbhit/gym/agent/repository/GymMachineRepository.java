package dev.surbhit.gym.agent.repository;

import dev.surbhit.gym.agent.model.db.GymMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GymMachineRepository extends JpaRepository<GymMachine, UUID> {
}
