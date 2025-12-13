package dev.surbhit.gym.agent.service;

import dev.surbhit.gym.agent.model.AppRegistration;
import dev.surbhit.gym.agent.model.db.AppUser;
import dev.surbhit.gym.agent.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {

    private AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }


    public void saveUserToDb(AppRegistration appRegistration){
        AppUser appUser = new AppUser();
        appUser.setEmail(appRegistration.getEmail());
        appUser.setFirstName(appRegistration.getFirstName());
        appUser.setLastName(appRegistration.getLastName());
        appUser.setPhoneNumber(appRegistration.getPhoneNumber());
        appUser.setPassWordHash(appRegistration.getPassword());
        appUserRepository.save(appUser);
    }

    public Optional<?> getUserDetails(String email){
        return appUserRepository.findByEmail(email);
    }
}
