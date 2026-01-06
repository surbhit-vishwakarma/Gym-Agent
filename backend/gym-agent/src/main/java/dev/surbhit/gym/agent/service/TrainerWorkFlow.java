package dev.surbhit.gym.agent.service;
import com.embabel.agent.core.*;
import com.embabel.agent.api.common.autonomy.AgentInvocation;
import dev.surbhit.gym.agent.mapper.FinalWorkoutPlan;
import dev.surbhit.gym.agent.mapper.UserFitnessContext;
import dev.surbhit.gym.agent.mapper.WorkoutPlan;
import dev.surbhit.gym.agent.model.db.Calorie;
import dev.surbhit.gym.agent.model.db.DailyCalorie;
import dev.surbhit.gym.agent.repository.CalorieRepository;
import dev.surbhit.gym.agent.repository.DailyCalorieRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerWorkFlow {


    private final CalorieRepository calorieRepository ;
    private final DailyCalorieRepository dailyCalorieRepository;
    private final AgentPlatform agentPlatform;

    public TrainerWorkFlow(CalorieRepository calorieRepository, DailyCalorieRepository dailyCalorieRepository, AgentPlatform agentPlatform) {
        this.calorieRepository = calorieRepository;
        this.dailyCalorieRepository = dailyCalorieRepository;
        this.agentPlatform = agentPlatform;
    }

    public WorkoutPlan run(UUID userId) {
        Optional<Calorie> calorie = calorieRepository.findByAppUser_UserId(userId);
        if(calorie.isEmpty()){
            return null;
        }

        List<DailyCalorie> dailyCalorie = dailyCalorieRepository.findByUser_UserId(userId);
        Calorie currentUserCalorie = calorie.get();
        UserFitnessContext userFitnessContext = getUserFitnessContext(userId, currentUserCalorie, dailyCalorie);


        FinalWorkoutPlan result =
                AgentInvocation.create(agentPlatform, FinalWorkoutPlan.class)
                        .invoke(userFitnessContext);

        return result.plan();
    }

    @NotNull
    private static UserFitnessContext getUserFitnessContext(UUID userId, Calorie calorie, List<DailyCalorie> dailyCalorie) {
        return new UserFitnessContext(userId, calorie.getAppUser().getFirstName(),
                calorie.getAge(), calorie.getHeight(),
                calorie.getWeight(), calorie.getTarget(),
                calorie.getAppUser().getGym().getName(), calorie.getAppUser().getGym().getMachines(), dailyCalorie);
    }
}