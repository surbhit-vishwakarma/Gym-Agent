package dev.surbhit.gym.agent.controller;

import dev.surbhit.gym.agent.mapper.CreateGymRequest;
import dev.surbhit.gym.agent.mapper.GymListResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GymController {

    ResponseEntity<String> createGym(@Valid @RequestBody CreateGymRequest dto);


    ResponseEntity<List<GymListResponse>> getListOfGyms();
}
