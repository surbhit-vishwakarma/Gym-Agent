package dev.surbhit.gym.agent.service;

import dev.surbhit.gym.agent.agent.TrainerAgent;
import dev.surbhit.gym.agent.mapper.UserFitnessContext;
import dev.surbhit.gym.agent.mapper.WorkoutPlan;
import dev.surbhit.gym.agent.model.db.Calorie;
import dev.surbhit.gym.agent.model.db.DailyCalorie;
import dev.surbhit.gym.agent.repository.CalorieRepository;
import dev.surbhit.gym.agent.repository.DailyCalorieRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerWorkFlow {

    private final TrainerAgent agent;

    private final CalorieRepository calorieRepository ;
    private final DailyCalorieRepository dailyCalorieRepository;

    public TrainerWorkFlow(TrainerAgent agent, CalorieRepository calorieRepository, DailyCalorieRepository dailyCalorieRepository) {
        this.agent = agent;
        this.calorieRepository = calorieRepository;
        this.dailyCalorieRepository = dailyCalorieRepository;
    }

    public WorkoutPlan run(UUID userId) {

        Optional<Calorie> calorie = calorieRepository.findByAppUser_UserId(userId);
        if(calorie.isEmpty()){
            return null;
        }


        List<DailyCalorie> dailyCalorie = dailyCalorieRepository.findByUser_UserId(userId);
        Calorie currentUserCalorieDetails = calorie.get();
        UserFitnessContext userFitnessContext = new UserFitnessContext(userId, currentUserCalorieDetails.getAppUser().getFirstName(),
                currentUserCalorieDetails.getAge(), currentUserCalorieDetails.getHeight(),
                currentUserCalorieDetails.getWeight(), currentUserCalorieDetails.getTarget(),
                currentUserCalorieDetails.getAppUser().getGym().getName(), currentUserCalorieDetails.getAppUser().getGym().getMachines(),dailyCalorie);

//        // Analyze calorie history
//        if (agent.isUserUnderEating(userFitnessContext)) {
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

        // Generate plan
        WorkoutPlan plan = agent.generatePlan(userFitnessContext);

//        // Validate safety
//        if (!agent.isPlanSafe(plan, currentUserCalorieDetails)) {
//            context = agent.lowerIntensity(currentUserCalorieDetails);
//            plan = agent.generatePlan(currentUserCalorieDetails);
//        }

        // Save
        agent.savePlan(plan);

        return plan;
    }
}