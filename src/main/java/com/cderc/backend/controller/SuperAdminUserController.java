package com.cderc.backend.controller;
import com.cderc.backend.dto.CreateAdminRequest;
import com.cderc.backend.dto.UserResponse;
import com.cderc.backend.mapper.UserMapper;
import com.cderc.backend.model.Organization;
import com.cderc.backend.model.Role;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.OrganizationRepository;
import com.cderc.backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/super-admin")
public class SuperAdminUserController {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminUserController(UserRepository userRepository,
                                    OrganizationRepository organizationRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(
            summary = "Admin anlegen",
            description = """
        Mit diesem Endpunkt legt man ein User/Admin für ein organisation an.

        Testdaten:
        Email: test@test.com
        Passwort: Test123!

        Bei erfolgreicher Anmeldung wird ein JWT-Token zurückgegeben.
        Diesen Token anschließend für alle geschützten Endpunkte verwenden.
        """
    )
//    @PostMapping("/admins")
//    public UserResponse createAdmin(@RequestBody CreateAdminRequest request) {
//
//        if (userRepository.existsByEmail(request.getEmail())) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        Organization organization = organizationRepository.findById(request.getOrganizationId())
//                .orElseThrow(() -> new RuntimeException("Organization not found"));
//
//        User admin = new User();
//        admin.setName(request.getName());
//        admin.setEmail(request.getEmail());
//        admin.setPassword(passwordEncoder.encode(request.getPassword()));
//        admin.setRole(Role.ADMIN);
//        admin.setOrganization(organization);
//
//        User savedAdmin =  userRepository.save(admin);
//        return UserMapper.toResponse(savedAdmin);
//    }


    @PostMapping("/organizations/{organizationId}/admins")
    public UserResponse createAdmin(
            @PathVariable Long organizationId,
            @RequestBody CreateAdminRequest request) {
        String email = normalizeEmail(request.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        Organization organization = organizationRepository
                .findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        User admin = new User();
        admin.setName(request.getName().trim());
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setOrganization(organization);

        User savedAdmin = userRepository.save(admin);

        return UserMapper.toResponse(savedAdmin);
    }

    @PutMapping("/organizations/{organizationId}/admins/{adminId}")
    public UserResponse updateAdmin(
            @PathVariable Long organizationId,
            @PathVariable Long adminId,
            @RequestBody CreateAdminRequest request) {
        String email = normalizeEmail(request.getEmail());

        Organization organization = organizationRepository
                .findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only organization admins can be updated here");
        }
        if (admin.getOrganization() == null || !admin.getOrganization().getId().equals(organization.getId())) {
            throw new RuntimeException("Admin does not belong to this organization");
        }
        if (userRepository.existsByEmailAndIdNot(email, adminId)) {
            throw new RuntimeException("Email already exists");
        }

        admin.setName(request.getName().trim());
        admin.setEmail(email);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User savedAdmin = userRepository.save(admin);
        return UserMapper.toResponse(savedAdmin);
    }

    @DeleteMapping("/organizations/{organizationId}/admins/{adminId}")
    public void deleteAdmin(
            @PathVariable Long organizationId,
            @PathVariable Long adminId) {

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only organization admins can be deleted here");
        }
        if (admin.getOrganization() == null || !admin.getOrganization().getId().equals(organizationId)) {
            throw new RuntimeException("Admin does not belong to this organization");
        }

        userRepository.delete(admin);
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
