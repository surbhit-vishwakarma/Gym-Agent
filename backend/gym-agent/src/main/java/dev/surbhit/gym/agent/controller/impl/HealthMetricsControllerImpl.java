package dev.surbhit.gym.agent.controller.impl;

import dev.surbhit.gym.agent.controller.HealthMetricsController;
import dev.surbhit.gym.agent.mapper.CalorieIntakeRequest;
import dev.surbhit.gym.agent.mapper.CalorieRequest;
import dev.surbhit.gym.agent.mapper.StringResponse;
import dev.surbhit.gym.agent.model.db.DailyCalorie;
import dev.surbhit.gym.agent.service.HealthMetricsService;
import dev.surbhit.gym.agent.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app/health")
public class HealthMetricsControllerImpl implements HealthMetricsController {

    private final HealthMetricsService healthMetricsService;

    public HealthMetricsControllerImpl(HealthMetricsService healthMetricsService) {
        this.healthMetricsService = healthMetricsService;
    }

    @Override
    @PostMapping("/calorie")
    @PreAuthorize("hasRole('NORMAL_USER')")
    public ResponseEntity<StringResponse> calculateCalories(@RequestBody CalorieRequest request) {
        UUID userID = SecurityUtils.getCurrentUserId();
        String res = healthMetricsService.addUserCalorie(request, userID);
        StringResponse response = new StringResponse(res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PostMapping("/calorie/user")
    @PreAuthorize("hasRole('NORMAL_USER')")
    public ResponseEntity<StringResponse> addCalorie(CalorieIntakeRequest calorieIntakeRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        String res = healthMetricsService.addDailyCalorie(calorieIntakeRequest,userId);
        StringResponse response = new StringResponse(res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('NORMAL_USER')")
    @GetMapping("/calories/user")
    public ResponseEntity<List<DailyCalorie>> getCalorieHistory() {
        UUID userId = SecurityUtils.getCurrentUserId();
        List<DailyCalorie> dailyCalories = healthMetricsService.getUserCalorieHistory(userId);
        return new ResponseEntity<>(dailyCalories,HttpStatus.OK);
    }
}
