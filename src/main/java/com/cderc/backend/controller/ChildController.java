package com.cderc.backend.controller;
import com.cderc.backend.dto.ChildRequest;
import com.cderc.backend.dto.ChildResponse;
import com.cderc.backend.mapper.ChildMapper;
import com.cderc.backend.model.Child;
import com.cderc.backend.model.User;
import com.cderc.backend.security.CustomUserDetails;
import com.cderc.backend.service.ChildService;
import com.cderc.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Children",
        description = "Kinderverwaltung"
)
@RestController
@RequestMapping("/api/children")
@CrossOrigin
public class ChildController {

    @Autowired
    private ChildService childService;
    @Autowired
    private UserService userService;


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

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        System.out.println("USER-MAIL = " + user.getEmail());
        System.out.println("USER-ORG = " + user.getOrganization());

        Child child = ChildMapper.toEntity(request);
        child.setOrganization(user.getOrganization());

        Child savedChild = childService.createChild(child);

        return ChildMapper.toResponse(savedChild);
    }

    @GetMapping
    public List<ChildResponse> getAllChildren(Authentication authentication) {

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        return childService.findByOrganizationId(user.getOrganization().getId())
                .stream()
                .map(ChildMapper::toResponse)
                .toList();
    }
    @GetMapping("/{id}")
    public ChildResponse  getChildById(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);


        Child child = childService.findByIdAndOrganizationId(
                id,
                user.getOrganization().getId()
        );

        return ChildMapper.toResponse(child);
    }

    @PutMapping("/{id}")
    public ChildResponse  updateChild(@PathVariable Long id,
                             @RequestBody ChildRequest request,
                             Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        Child updatedChild = ChildMapper.toEntity(request);

        Child savedChild = childService.updateChild(
                id,
                user.getOrganization().getId(),
                updatedChild
        );

        return ChildMapper.toResponse(savedChild);
    }

    @DeleteMapping("/{id}")
    public void deleteChild(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        childService.deleteChild(id, user.getOrganization().getId());
    }

}
