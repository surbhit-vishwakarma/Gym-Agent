package dev.surbhit.gym.agent.controller.impl;


import dev.surbhit.gym.agent.controller.AppController;
import dev.surbhit.gym.agent.model.AppLogin;
import dev.surbhit.gym.agent.model.AppRegistration;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppControllerImpl implements AppController {

    @Override
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody  AppRegistration dto) {
        return new ResponseEntity<>(dto.toString(), HttpStatus.OK);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppLogin appLogin) {
        return new ResponseEntity<>("Login success", HttpStatus.OK);
    }


}