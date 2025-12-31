package dev.surbhit.gym.agent.controller.impl;


import dev.surbhit.gym.agent.controller.AppController;
import dev.surbhit.gym.agent.model.AppLogin;
import dev.surbhit.gym.agent.model.AppRegistration;
import dev.surbhit.gym.agent.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppControllerImpl implements AppController {

    @Autowired
    AppUserService appUserService;

    @Override
    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody  AppRegistration dto) {
        try{
        appUserService.saveUserToDb(dto);}
        catch (Exception e){
            return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto.toString(), HttpStatus.OK);
    }

    @Override
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody AppLogin appLogin) {
        String token = appUserService.userLogin(appLogin);
        if(token == null){
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}