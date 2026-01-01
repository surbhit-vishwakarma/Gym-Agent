package dev.surbhit.gym.agent.service;

import dev.surbhit.gym.agent.mapper.CreateGymRequest;
import dev.surbhit.gym.agent.mapper.GymListResponse;
import dev.surbhit.gym.agent.mapper.MachineDto;
import dev.surbhit.gym.agent.mapper.SelectGymDto;
import dev.surbhit.gym.agent.model.db.AppUser;
import dev.surbhit.gym.agent.model.db.Gym;
import dev.surbhit.gym.agent.model.db.GymMachine;
import dev.surbhit.gym.agent.repository.AppUserRepository;
import dev.surbhit.gym.agent.repository.GymMachineRepository;
import dev.surbhit.gym.agent.repository.GymRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GymService {

    private final GymRepository gymRepository;

    private final GymMachineRepository gymMachineRepository;

    private final AppUserRepository appUserRepository;

    public GymService(GymRepository gymRepository, GymMachineRepository gymMachineRepository, AppUserRepository appUserRepository) {
        this.gymRepository = gymRepository;
        this.gymMachineRepository = gymMachineRepository;
        this.appUserRepository = appUserRepository;
    }

    public void registerGym(CreateGymRequest createGymRequest, UUID ownerId) {
        Gym gym = new Gym();
        gym.setLocation(createGymRequest.location());
        gym.setName(createGymRequest.name());
        gym.setOwnerId(ownerId);

        gymRepository.save(gym);
    }

    public List<GymListResponse> findAllGyms() {
        List<GymListResponse> gymListResponsesMain = new ArrayList<>();
        List<Gym> gymList = gymRepository.findAll();
        for (Gym gym : gymList) {
            GymListResponse gymListResponse = new GymListResponse();
            gymListResponse.setGym(gym);
            gymListResponsesMain.add(gymListResponse);
        }

        return gymListResponsesMain;
    }

    public boolean registerMachine(MachineDto machineDto) {
        GymMachine gymMachine = new GymMachine();

        Optional<Gym> gym = gymRepository.findById(machineDto.gymId());

        if (gym.isPresent()) {
            Gym currentGym = gym.get();
            gymMachine.setCategory(machineDto.category());
            gymMachine.setBrand(machineDto.brand());
            gymMachine.setName(machineDto.name());
            gymMachine.setGym(currentGym);
            gymMachineRepository.save(gymMachine);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean joinGym(SelectGymDto selectGymDto, UUID userId) {
        Optional<Gym> gym = gymRepository.findById(selectGymDto.gymId());
        if(gym.isPresent()){
            Optional<AppUser> user = appUserRepository.findById(userId);
            if(user.isPresent()){
                AppUser appUser = user.get();
                appUser.setGym(gym.get());
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
