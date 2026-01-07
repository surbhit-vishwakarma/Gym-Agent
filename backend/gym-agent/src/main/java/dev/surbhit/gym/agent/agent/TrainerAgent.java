package dev.surbhit.gym.agent.agent;

import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.AchievesGoal;
import dev.surbhit.gym.agent.mapper.*;
import dev.surbhit.gym.agent.model.db.DailyCalorie;

import static dev.surbhit.gym.agent.constants.CalorieConstants.*;

@Agent(
        name = "gym-trainer-agent",
        description = "Creates safe, personalized gym workout plans",
        version = "1.0.0",
        beanName = "trainerAgent"
)
public class TrainerAgent {

    private final TrainerBrain trainerBrain;

    public TrainerAgent(TrainerBrain trainerBrain) {
        this.trainerBrain = trainerBrain;
    }

    @Action
    public EffectiveFitnessContext decideGoal(UserFitnessContext ctx) {
        System.out.println("✅ decideGoal");

        double avgCalories = ctx.last7DaysCalories()
                .stream()
                .mapToInt(DailyCalorie::getCurrentCalorie)
                .average()
                .orElse(0);

        String effectiveGoal =
                avgCalories < 1800 ? MAINTAIN_WEIGHT : ctx.goal();

        return new EffectiveFitnessContext(ctx, effectiveGoal);
    }

    @Action
    public WorkoutPlan generatePlan(EffectiveFitnessContext ctx) {
        System.out.println("✅ generatePlan");
        return trainerBrain.generatePlan(ctx.originalContext());
    }

    @Action
    public SafeWorkoutPlan regenerateIfUnsafe(
            WorkoutPlan plan,
            EffectiveFitnessContext ctx
    ) {
        System.out.println("✅ regenerateIfUnsafe");

        if (isPlanSafe(plan, ctx.effectiveGoal())) {
            return new SafeWorkoutPlan(plan);
        }

        UserFitnessContext saferCtx =
                new UserFitnessContext(
                        ctx.originalContext().userId(),
                        ctx.originalContext().userName(),
                        ctx.originalContext().age(),
                        ctx.originalContext().heightCm(),
                        ctx.originalContext().weightKg(),
                        ctx.effectiveGoal(),
                        ctx.originalContext().gymName(),
                        ctx.originalContext().availableMachines(),
                        ctx.originalContext().last7DaysCalories()
                );

        WorkoutPlan saferPlan = trainerBrain.generatePlan(saferCtx);
        return new SafeWorkoutPlan(saferPlan);
    }

    private boolean isPlanSafe(WorkoutPlan plan, String goal) {

        if (plan.trainingDaysPerWeek() > 5) return false;
        if (plan.totalSetsPerDay() > 25) return false;

        return switch (goal) {
            case WEIGHT_LOSS -> plan.maxIntensity() <= 7;
            case WEIGHT_GAIN -> plan.maxIntensity() <= 8;
            case MAINTAIN_WEIGHT -> plan.maxIntensity() <= 5;
            default -> true;
        };
    }

    @AchievesGoal(description = "Workout plan safely saved")
    @Action
    public FinalWorkoutPlan savePlan(SafeWorkoutPlan safePlan) {
        System.out.println("✅ savePlan");
        return new FinalWorkoutPlan(safePlan.plan());
    }
}
