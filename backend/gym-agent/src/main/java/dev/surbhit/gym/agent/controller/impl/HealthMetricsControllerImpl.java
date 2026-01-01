package dev.surbhit.gym.agent.controller.impl;

import dev.surbhit.gym.agent.controller.HealthMetricsController;
import dev.surbhit.gym.agent.mapper.CalorieRequest;
import dev.surbhit.gym.agent.mapper.StringResponse;
import dev.surbhit.gym.agent.service.HealthMetricsService;
import dev.surbhit.gym.agent.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @Override
//    public ResponseEntity<List<CalorieHistoryResponse>> getCalorieHistory() {
//        return null;
//    }
}
