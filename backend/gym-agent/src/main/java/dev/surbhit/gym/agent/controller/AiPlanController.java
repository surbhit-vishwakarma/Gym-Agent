package dev.surbhit.gym.agent.controller;

import dev.surbhit.gym.agent.mapper.StringResponse;
import org.springframework.http.ResponseEntity;

public interface AiPlanController {

    ResponseEntity<StringResponse> generate();
}
