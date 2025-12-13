package dev.surbhit.gym.agent.controller.impl;


import dev.surbhit.gym.agent.controller.AppController;
import dev.surbhit.gym.agent.model.AppLogin;
import dev.surbhit.gym.agent.model.AppRegistration;
import dev.surbhit.gym.agent.model.db.AppUser;
import dev.surbhit.gym.agent.security.JwtProvider;
import dev.surbhit.gym.agent.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AppControllerImpl implements AppController {

    @Autowired
    AppUserService appUserService;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody  AppRegistration dto) {
        appUserService.saveUserToDb(dto);
        return new ResponseEntity<>(dto.toString(), HttpStatus.OK);
    }

    @Override
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody AppLogin appLogin) {
        Optional<?> user = appUserService.getUserDetails(appLogin.getEmail());
        String token;
        if(user.isPresent()){
            AppUser user1 = (AppUser) user.get();
             token = jwtProvider.createToken(user1.getUserId(),user1.getEmail(),"ADMIN");
            System.out.println(jwtProvider.getRolesFromToken(token));
        }else{
            return new ResponseEntity<>("NO", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>("Hello",HttpStatus.OK);
    }
}