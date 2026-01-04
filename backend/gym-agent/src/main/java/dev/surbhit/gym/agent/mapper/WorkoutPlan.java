package dev.surbhit.gym.agent.mapper;

import java.util.List;

public record WorkoutPlan(
        int trainingDaysPerWeek,
        int maxIntensity,
        int totalSetsPerDay,
        List<String> exercises
) {}