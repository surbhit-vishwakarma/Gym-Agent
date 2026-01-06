package dev.surbhit.gym.agent.mapper;

public record EffectiveFitnessContext(
        UserFitnessContext originalContext,
        String effectiveGoal
) {}