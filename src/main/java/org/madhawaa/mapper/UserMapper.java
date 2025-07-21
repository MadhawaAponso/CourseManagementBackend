package org.madhawaa.mapper;

import org.madhawaa.dto.responseDTO.UserResponseDTO;
import org.madhawaa.dto.requestDTO.UserRequestDTO;
import org.madhawaa.entity.User;

public class UserMapper {

    public static void toDTO(User user, UserResponseDTO dto) {
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
    }

    public static void fromDTO(UserRequestDTO dto, User user) {
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword()); // hashed in service
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setIsActive(true);
    }
}
