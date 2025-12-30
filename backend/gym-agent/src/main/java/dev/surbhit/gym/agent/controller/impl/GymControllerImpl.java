package dev.surbhit.gym.agent.controller.impl;

import dev.surbhit.gym.agent.controller.GymController;
import dev.surbhit.gym.agent.mapper.CreateGymRequest;
import dev.surbhit.gym.agent.mapper.GymListResponse;
import dev.surbhit.gym.agent.service.GymService;
import dev.surbhit.gym.agent.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app")
public class GymControllerImpl implements GymController {

    private GymService gymService;

    public GymControllerImpl(GymService gymService) {
        this.gymService = gymService;
    }

    @PostMapping("/gym")
    @Override
    @PreAuthorize("hasRole('GYM_OWNER')")
    public ResponseEntity<String> createGym(CreateGymRequest dto) {
        try{
            UUID ownerId = SecurityUtils.getCurrentUserId();
            gymService.registerGym(dto, ownerId);
        }catch (Exception e){
            return new ResponseEntity<>("Gym already exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Gym Saved", HttpStatus.OK);
    }

    @GetMapping("/gyms")
    @Override
    @PreAuthorize("hasRole('NORMAL_USER')")
    public ResponseEntity<List<GymListResponse>> getListOfGyms() {

        List<GymListResponse> gymListResponses= gymService.findAllGyms();
        return new ResponseEntity<>(gymListResponses, HttpStatus.OK);
    }
}
