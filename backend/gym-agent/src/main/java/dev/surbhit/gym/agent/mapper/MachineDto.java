package dev.surbhit.gym.agent.mapper;

import java.util.UUID;

public record MachineDto(UUID gymId, String name, String category, String brand) {
}
