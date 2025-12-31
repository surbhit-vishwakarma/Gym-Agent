package dev.surbhit.gym.agent.controller.impl;

import dev.surbhit.gym.agent.controller.GymController;
import dev.surbhit.gym.agent.mapper.CreateGymRequest;
import dev.surbhit.gym.agent.mapper.GymListResponse;
import dev.surbhit.gym.agent.mapper.MachineDto;
import dev.surbhit.gym.agent.service.GymService;
import dev.surbhit.gym.agent.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createGym(@RequestBody CreateGymRequest dto) {
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

    @PostMapping("/machine")
    @PreAuthorize("hasRole('GYM_OWNER')")
    public ResponseEntity<String>createMachine(@RequestBody MachineDto machineDto){
        boolean check = gymService.registerMachine(machineDto);
        if(check)
            return new ResponseEntity<>("Machine added", HttpStatus.OK);
        else
            return new ResponseEntity<>("Machine cant be added is gym not present", HttpStatus.BAD_REQUEST);
    }
}
