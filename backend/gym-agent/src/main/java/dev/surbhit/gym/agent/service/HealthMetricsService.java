package dev.surbhit.gym.agent.service;

import dev.surbhit.gym.agent.mapper.CalorieIntakeRequest;
import dev.surbhit.gym.agent.mapper.CalorieRequest;
import dev.surbhit.gym.agent.model.db.AppUser;
import dev.surbhit.gym.agent.model.db.Calorie;
import dev.surbhit.gym.agent.model.db.DailyCalorie;
import dev.surbhit.gym.agent.repository.AppUserRepository;
import dev.surbhit.gym.agent.repository.CalorieRepository;
import dev.surbhit.gym.agent.repository.DailyCalorieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static dev.surbhit.gym.agent.constants.CalorieConstants.*;

@Service
public class HealthMetricsService {

    private final CalorieRepository calorieRepository;
    private final AppUserRepository appUserRepository;
    private final DailyCalorieRepository dailyCalorieRepository;

    public HealthMetricsService(CalorieRepository calorieRepository, AppUserRepository appUserRepository, DailyCalorieRepository dailyCalorieRepository) {
        this.calorieRepository = calorieRepository;
        this.appUserRepository = appUserRepository;
        this.dailyCalorieRepository = dailyCalorieRepository;
    }

    //    BMR (Mifflin–St Jeor) method used to calculate target calories.
    public String addUserCalorie(CalorieRequest request, UUID userId) {

        Optional<Calorie> userCalorie = calorieRepository.findByAppUser_UserId(userId);
        if (userCalorie.isPresent())
            return "Cannot add calorie as it is already present";

        double bmr;

        if (Objects.equals(request.gender(), MALE)) {
            bmr = 10 * request.weight() + 6.25 * request.height() - 5 * request.age() + 5;
        } else {
            bmr = 10 * request.weight() + 6.25 * request.height() - 5 * request.age() - 161;
        }

        double activityMultiplier = switch (request.activityLevel()) {
            case SEDENTARY -> 1.2;
            case LIGHT -> 1.375;
            case MODERATE -> 1.55;
            case VERY_ACTIVE -> 1.725;
            default -> throw new IllegalStateException("Unexpected value: " + request.activityLevel());
        };

        double tdee = bmr * activityMultiplier;
        int targetCalories = (int) switch (request.target()) {
            case WEIGHT_LOSS -> tdee - 500;
            case WEIGHT_GAIN -> tdee + 300;
            case MAINTAIN_WEIGHT -> tdee;
            default -> throw new IllegalStateException("Unexpected value: " + request.target());
        };

        Calorie calorie = new Calorie();

        calorie.setTargetCalories(targetCalories);

        calorie.setActivityLevel(request.activityLevel());
        calorie.setGender(request.gender());
        calorie.setWeight(request.weight());
        calorie.setAge(request.age());
        calorie.setHeight(request.height());
        calorie.setTarget(request.target());
        calorie.setDate(LocalDate.now());

        var dailyCalorie = new DailyCalorie();
        dailyCalorie.setCurrentCalorie(0);
        dailyCalorie.setTargetCalorie(targetCalories);
        dailyCalorie.setDate(LocalDate.now());

        Optional<AppUser> appUser = appUserRepository.findById(userId);
        if (appUser.isPresent()) {
            calorie.setAppUser(appUser.get());
            dailyCalorie.setAppUser(appUser.get());
            calorieRepository.save(calorie);
            dailyCalorieRepository.save(dailyCalorie);

            return "Added Calorie for current user";
        } else
            return "Uer not found please try again";
    }

    @Transactional
    public String addDailyCalorie(
            CalorieIntakeRequest request,
            UUID userId
    ) {
        LocalDate today = LocalDate.now();

        DailyCalorie dailyCalorie = dailyCalorieRepository
                .findByUser_UserIdAndDate(userId, today)
                .orElseGet(() -> {
                    DailyCalorie dc = new DailyCalorie();

                    Calorie calorie = calorieRepository
                            .findByAppUser_UserId(userId)
                            .orElseThrow(() ->
                                    new RuntimeException("Target calorie not found")
                            );

                    dc.setAppUser(calorie.getAppUser());
                    dc.setTargetCalorie(calorie.getTargetCalories());
                    dc.setCurrentCalorie(0);
                    dc.setDate(today);

                    return dc;
                });

        dailyCalorie.setCurrentCalorie(
                dailyCalorie.getCurrentCalorie() + request.calories()
        );

        dailyCalorieRepository.save(dailyCalorie);

        return "Daily calorie updated successfully";
    }
}
