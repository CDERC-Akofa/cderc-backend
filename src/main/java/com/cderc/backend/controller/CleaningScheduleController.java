package com.cderc.backend.controller;

import com.cderc.backend.dto.CleaningScheduleResponse;
import com.cderc.backend.dto.GenerateCleaningScheduleRequest;
import com.cderc.backend.model.CleaningSchedule;
import com.cderc.backend.model.User;
import com.cderc.backend.service.CleaningScheduleService;
import com.cderc.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cleaning-schedules")
public class CleaningScheduleController {

        private final CleaningScheduleService cleaningScheduleService;
        private final UserService userService;

    public CleaningScheduleController(CleaningScheduleService cleaningScheduleService,
            UserService userService) {
        this.cleaningScheduleService = cleaningScheduleService;
        this.userService = userService;
    }

        @PostMapping("/generate")
        public List<CleaningScheduleResponse> generate(@RequestBody GenerateCleaningScheduleRequest request,
                                                       Authentication authentication) {
            User user = userService.findByEmail(authentication.getName());

            return cleaningScheduleService.generate(
                            request.getStartDate(),
                            request.getNumberOfWeeks(),
                            user.getOrganization()
                    )
                    .stream()
                    .map(CleaningScheduleResponse::toResponse)
                    .toList();
        }

    @GetMapping
    public List<CleaningScheduleResponse> getAll(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return cleaningScheduleService.findAll(user.getOrganization().getId())
                .stream()
                .map(CleaningScheduleResponse::toResponse)
                .toList();
    }
}
