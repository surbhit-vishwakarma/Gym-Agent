//package dev.surbhit.gym.agent.controller.impl;
//
//import dev.surbhit.gym.agent.controller.AiPlanController;
//import dev.surbhit.gym.agent.mapper.StringResponse;
//import dev.surbhit.gym.agent.mapper.UserFitnessContext;
//import dev.surbhit.gym.agent.service.TrainerWorkflow;
//import dev.surbhit.gym.agent.utils.SecurityUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/v1/app/plan")
//public class AiPlanControllerImpl implements AiPlanController {
//
//    private final TrainerWorkflow workflow;
//
//    public AiPlanControllerImpl(TrainerWorkflow workflow) {
//        this.workflow = workflow;
//    }
//    @Override
//    @GetMapping("/generate")
//    @PreAuthorize("hasRole('GYM_OWNER')")
//    public ResponseEntity<StringResponse> generate() {
//        String res = "";
//        UUID userId = SecurityUtils.getCurrentUserId();
//        res=  workflow.run(userId);
//        StringResponse stringResponse = new StringResponse(res);
//        return new ResponseEntity<>(stringResponse, HttpStatus.OK);
//    }
//}
