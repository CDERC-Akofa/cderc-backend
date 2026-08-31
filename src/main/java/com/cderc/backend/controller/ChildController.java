package com.cderc.backend.controller;
import com.cderc.backend.dto.ChildRequest;
import com.cderc.backend.dto.ChildResponse;
import com.cderc.backend.mapper.ChildMapper;
import com.cderc.backend.model.Child;
import com.cderc.backend.model.Role;
import com.cderc.backend.model.User;
import com.cderc.backend.service.ChildService;
import com.cderc.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(
        name = "Children",
        description = "Kinderverwaltung"
)
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/children")
@CrossOrigin
public class ChildController {

    @Autowired
    private ChildService childService;
    @Autowired
    private UserService userService;

    private User authenticatedUser(Authentication authentication) {
        return userService.findByEmail(authentication.getName());
    }

    private Long requireOrganizationId(User user) {
        if (user.getOrganization() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User has no organization");
        }

        return user.getOrganization().getId();
    }

    @Operation(
            summary = "Kind anlegen",
            description = """
        Erstellt ein neues Kind.

        Voraussetzungen

        - Benutzer muss angemeldet sein.
        - JWT Token erforderlich.
        - Organisation muss vorhanden sein.

        Nach erfolgreicher Erstellung wird das Kind zurückgegeben.
        """
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Kind erfolgreich erstellt"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ungültige Eingabedaten"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Nicht authentifiziert"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Keine Berechtigung"
            )
    })
    @PostMapping
    public ChildResponse createChild(@RequestBody ChildRequest request, Authentication authentication) {

        System.out.println("CREATE CHILD CALLED");
        System.out.println("AUTH NAME = " + authentication.getName());
        System.out.println("AUTHORITIES = " + authentication.getAuthorities());

        User user = authenticatedUser(authentication);

        System.out.println("USER-MAIL = " + user.getEmail());
        System.out.println("USER-ORG = " + user.getOrganization());

        Child child = ChildMapper.toEntity(request);
        requireOrganizationId(user);
        child.setOrganization(user.getOrganization());

        Child savedChild = childService.createChild(child);

        return ChildMapper.toResponse(savedChild);
    }

    @GetMapping
    public List<ChildResponse> getAllChildren(Authentication authentication) {

        User user = authenticatedUser(authentication);

        List<Child> children = user.getRole() == Role.SUPER_ADMIN
                ? childService.findAll()
                : childService.findByOrganizationId(requireOrganizationId(user));

        return children
                .stream()
                .map(ChildMapper::toResponse)
                .toList();
    }
    @GetMapping("/{id}")
    public ChildResponse  getChildById(@PathVariable Long id, Authentication authentication) {
        User user = authenticatedUser(authentication);

        Child child = user.getRole() == Role.SUPER_ADMIN
                ? childService.findAll().stream()
                        .filter(foundChild -> foundChild.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Child not found"))
                : childService.findByIdAndOrganizationId(
                        id,
                        requireOrganizationId(user)
                );

        return ChildMapper.toResponse(child);
    }

    @PutMapping("/{id}")
    public ChildResponse  updateChild(@PathVariable Long id,
                             @RequestBody ChildRequest request,
                             Authentication authentication) {
        User user = authenticatedUser(authentication);

        Child updatedChild = ChildMapper.toEntity(request);

        Child savedChild = childService.updateChild(
                id,
                requireOrganizationId(user),
                updatedChild
        );

        return ChildMapper.toResponse(savedChild);
    }

    @DeleteMapping("/{id}")
    public void deleteChild(@PathVariable Long id, Authentication authentication) {
        User user = authenticatedUser(authentication);

        childService.deleteChild(id, requireOrganizationId(user));
    }

}
