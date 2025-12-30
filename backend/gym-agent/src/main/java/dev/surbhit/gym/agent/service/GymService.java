package dev.surbhit.gym.agent.service;

import dev.surbhit.gym.agent.mapper.CreateGymRequest;
import dev.surbhit.gym.agent.mapper.GymListResponse;
import dev.surbhit.gym.agent.model.db.Gym;
import dev.surbhit.gym.agent.repository.GymRepository;
import dev.surbhit.gym.agent.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GymService {

    private GymRepository gymRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public GymService(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    public void registerGym(CreateGymRequest createGymRequest, UUID ownerId){
        Gym gym = new Gym();
        gym.setLocation(createGymRequest.location());
        gym.setName(createGymRequest.name());
        gym.setOwnerId(ownerId);

        gymRepository.save(gym);
    }

    public List<GymListResponse> findAllGyms(){
        List<GymListResponse> gymListResponsesMain = new ArrayList<>();
        List<Gym> gymList = gymRepository.findAll();
        for(Gym gym : gymList){
            GymListResponse gymListResponse = new GymListResponse();
            gymListResponse.setGym(gym);
            gymListResponsesMain.add(gymListResponse);
        }

        return gymListResponsesMain;
    }
}
