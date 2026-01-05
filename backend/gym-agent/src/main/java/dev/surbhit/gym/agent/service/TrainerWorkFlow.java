//package dev.surbhit.gym.agent.service;
//
//import dev.surbhit.gym.agent.agent.TrainerAgent;
//import dev.surbhit.gym.agent.mapper.UserFitnessContext;
//import dev.surbhit.gym.agent.mapper.WorkoutPlan;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class TrainerWorkflow {
//
//    private final TrainerAgent agent;
//
//    public TrainerWorkflow(TrainerAgent agent) {
//        this.agent = agent;
//    }
//
//    public WorkoutPlan run(UUID userId) {
//
//        // Analyze calorie history
//        if (agent.isUserUnderEating(context)) {
//            context = new UserFitnessContext(
//                    context.userId(),
//                    context.heightCm(),
//                    context.weightKg(),
//                    "recovery",
//                    context.last7DaysCalories(),
//                    context.availableMachines(),
//                    context.intensityCap()
//            );
//        }
//
//        // Generate plan
//        WorkoutPlan plan = agent.generatePlan(context);
//
//        // Validate safety
//        if (!agent.isPlanSafe(plan, context)) {
//            context = agent.lowerIntensity(context);
//            plan = agent.generatePlan(context);
//        }
//
//        // Save
//        agent.savePlan(plan);
//
//        return plan;
//    }
//}