package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.madhawaa.dto.requestDTO.UserRequestDTO;
import org.madhawaa.dto.responseDTO.UserResponseDTO;
import org.madhawaa.entity.User;
import org.madhawaa.mapper.UserMapper;
import org.madhawaa.repository.UserRepository;

@ApplicationScoped
public class AuthService {

    @Inject
    KeycloakAdminService keycloakAdminService;

    @Inject
    UserService userService;

    public UserResponseDTO registerUser(UserRequestDTO dto) throws Exception {
        // 1. Create user in DB and get userId
        UserResponseDTO createdUser = userService.createUser(dto);
        Integer userId = createdUser.getId();

        // 2. Create user in Keycloak
        String keycloakUserId = keycloakAdminService.createUserInKeycloak(dto);

        // 3. Assign role
        keycloakAdminService.assignRoleToUser(keycloakUserId, dto.getRole().name());

        // 4. Update custom attribute (userId)
        keycloakAdminService.updateUserAttribute(keycloakUserId, "userId", String.valueOf(userId));

        return createdUser;
    }

}
