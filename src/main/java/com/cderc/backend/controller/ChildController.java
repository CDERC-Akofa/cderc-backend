package com.cderc.backend.controller;
import com.cderc.backend.model.Child;
import com.cderc.backend.model.User;
import com.cderc.backend.security.CustomUserDetails;
import com.cderc.backend.service.ChildService;
import com.cderc.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/children")
@CrossOrigin
public class ChildController {

    @Autowired
    private ChildService childService;
    @Autowired
    private UserService userService;

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN') or hasRole('SOCIAL_WORKER')")
    public Child createChild(@RequestBody Child child, Authentication authentication) {
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        User user = userDetails.getUser();
        System.out.println("CREATE CHILD CALLED");
        System.out.println("AUTH NAME = " + authentication.getName());
        System.out.println("AUTHORITIES = " + authentication.getAuthorities());

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        System.out.println("USER-MAIL = " + user.getEmail());
        System.out.println("USER-ORG = " + user.getOrganization());

        child.setOrganization(user.getOrganization());
        return childService.createChild(child);
    }

    @GetMapping
    public List<Child> getAllChildren(Authentication authentication) {

//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        User user = userDetails.getUser();

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        // Nur Kinder der eigenen Organisation zurückgeben
        return childService.findByOrganizationId(user.getOrganization().getId());
    }
}
