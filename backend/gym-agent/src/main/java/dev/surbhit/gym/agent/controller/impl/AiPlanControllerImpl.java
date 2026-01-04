package dev.surbhit.gym.agent.controller.impl;

import dev.surbhit.gym.agent.controller.AiPlanController;
import dev.surbhit.gym.agent.mapper.StringResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app/plan")
public class AiPlanControllerImpl implements AiPlanController {


    @Override
    @GetMapping("/generate")
    @PreAuthorize("hasRole('GYM_OWNER')")
    public ResponseEntity<StringResponse> generate() {
        return null;
    }
}
