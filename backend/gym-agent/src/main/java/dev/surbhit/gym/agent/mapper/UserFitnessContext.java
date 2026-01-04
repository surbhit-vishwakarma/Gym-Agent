package dev.surbhit.gym.agent.mapper;


import java.util.List;
import java.util.UUID;

public record UserFitnessContext(
        UUID userId,
        String userName,
        int age,
        int heightCm,
        int weightKg,
        String goal,                 // fat_loss, muscle_gain, maintenance
        String gymName,
        List<String> availableMachines,
        List<Integer> last7DaysCalories
) {}
