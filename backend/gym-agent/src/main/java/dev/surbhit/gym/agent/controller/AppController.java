package dev.surbhit.gym.agent.controller;

import dev.surbhit.gym.agent.model.AppLogin;
import dev.surbhit.gym.agent.model.AppRegistration;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/app")   // allowed on interface
public interface AppController {

    @PostMapping("/register")
    ResponseEntity<String> register(@Valid @RequestBody AppRegistration dto);

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody AppLogin appLogin);
}