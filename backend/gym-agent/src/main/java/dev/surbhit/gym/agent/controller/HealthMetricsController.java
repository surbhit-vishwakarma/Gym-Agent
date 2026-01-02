package dev.surbhit.gym.agent.controller;

import dev.surbhit.gym.agent.mapper.CalorieIntakeRequest;
import dev.surbhit.gym.agent.mapper.CalorieRequest;
import dev.surbhit.gym.agent.mapper.StringResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface HealthMetricsController {
    public ResponseEntity<StringResponse> calculateCalories(@Valid @RequestBody CalorieRequest request);
//    public ResponseEntity<List<CalorieHistoryResponse>> getCalorieHistory();

    public ResponseEntity<StringResponse> addCalorie(@RequestBody CalorieIntakeRequest calorieIntakeRequest);
}
