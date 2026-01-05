package dev.surbhit.gym.agent.agent;

import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import dev.surbhit.gym.agent.mapper.UserFitnessContext;
import dev.surbhit.gym.agent.mapper.WorkoutPlan;


@Agent(description = "Agent to create gym plan")
public class TrainerAgent {

    private final TrainerBrain trainerBrain;

    public TrainerAgent(TrainerBrain trainerBrain) {
        this.trainerBrain = trainerBrain;
    }

    //  Analyze calorie history
    @Action
    public boolean isUserUnderEating(UserFitnessContext ctx) {
        double avgCalories = ctx.last7DaysCalories()
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

        return avgCalories < 1800;
    }

    //  Generate plan (normal / recovery decided by context)
    @Action
    public WorkoutPlan generatePlan(UserFitnessContext ctx) {
        return trainerBrain.generatePlan(ctx);
    }

    //  Validate plan safety (REAL RULES)
    @Action
    public boolean isPlanSafe(WorkoutPlan plan, UserFitnessContext ctx) {

        if (plan.trainingDaysPerWeek() > 5) return false;
        if (plan.totalSetsPerDay() > 25) return false;

        if (ctx.goal().equals("fat_loss") && plan.maxIntensity() > 7) return false;
        if (ctx.goal().equals("muscle_gain") && plan.maxIntensity() > 8) return false;
        if (ctx.goal().equals("recovery") && plan.maxIntensity() > 5) return false;

        return true;
    }

    //  Regenerate with lower intensity
    @Action
    public UserFitnessContext lowerIntensity(UserFitnessContext ctx) {
        return new UserFitnessContext(
                ctx.userId(),
                ctx.userName(),
                ctx.age(),
                ctx.heightCm(),
                ctx.weightKg(),
                "recovery",
                ctx.gymName(),
                ctx.availableMachines(),
                ctx.last7DaysCalories()
        );
    }

    // ↓ Save plan
    @Action
    public void savePlan(WorkoutPlan plan) {
        System.out.println("✅ Plan saved for user");
    }
}

