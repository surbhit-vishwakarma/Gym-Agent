package dev.surbhit.gym.agent.mapper;


import dev.surbhit.gym.agent.model.db.DailyCalorie;
import dev.surbhit.gym.agent.model.db.GymMachine;

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
        List<GymMachine> availableMachines,
        List<DailyCalorie> last7DaysCalories
) {}
