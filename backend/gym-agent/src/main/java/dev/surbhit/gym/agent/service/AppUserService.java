package dev.surbhit.gym.agent.service;

import dev.surbhit.gym.agent.model.AppLogin;
import dev.surbhit.gym.agent.model.AppRegistration;
import dev.surbhit.gym.agent.model.db.AppUser;
import dev.surbhit.gym.agent.repository.AppUserRepository;
import dev.surbhit.gym.agent.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder securityConfig){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = securityConfig;
    }


    public void saveUserToDb(AppRegistration appRegistration){
        AppUser appUser = new AppUser();
        appUser.setEmail(appRegistration.getEmail());
        appUser.setFirstName(appRegistration.getFirstName());
        appUser.setLastName(appRegistration.getLastName());
        appUser.setPhoneNumber(appRegistration.getPhoneNumber());
        appUser.setRole(appRegistration.getRole());
        appUser.setPassWordHash(passwordEncoder.encode(appRegistration.getPassword()));
        appUserRepository.save(appUser);
    }

    public String userLogin(AppLogin appLogin){
        Optional<?> user = appUserRepository.findByEmail(appLogin.getEmail());
        if(user.isPresent()){
            AppUser user1 = (AppUser) user.get();
            if(!passwordEncoder.matches(appLogin.getPassword(),user1.getPassWordHash()))
                return null;

            String token = jwtProvider.createToken(user1.getUserId(),user1.getEmail(),user1.getRole());
            System.out.println(jwtProvider.getRolesFromToken(token));

            return token;
        }else{
            return null;
        }
    }

    public Optional<?> getUserDetails(String email){
        return appUserRepository.findByEmail(email);
    }
}
