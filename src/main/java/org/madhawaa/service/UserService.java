package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.madhawaa.dto.requestDTO.UserRequestDTO;
import org.madhawaa.dto.responseDTO.UserResponseDTO;
import org.madhawaa.entity.User;
import org.madhawaa.enums.Role;
import org.madhawaa.mapper.UserMapper;
import org.madhawaa.repository.UserRepository;
import org.madhawaa.util.PasswordUtil;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public UserResponseDTO getById(Integer id) {
        User user = userRepository.findById(id);
        if (user == null) throw new NotFoundException("User not found");

        UserResponseDTO dto = new UserResponseDTO();
        UserMapper.toDTO(user, dto);
        return dto;
    }

    public List<UserResponseDTO> getAllByRole(Role role) {
        return userRepository.find("role", role)
                .stream()
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    UserMapper.toDTO(user, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = new User();
        UserMapper.fromDTO(dto, user);
        user.setPasswordHash(PasswordUtil.hash(dto.getPassword())); // 🔐 Secure password
        userRepository.persist(user);

        UserResponseDTO response = new UserResponseDTO();
        UserMapper.toDTO(user, response);
        return response;
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id);
        if (user == null) throw new NotFoundException("User not found");
        userRepository.delete(user);
    }

    public UserResponseDTO getProfile(Integer id) {
        return getById(id);
    }

    public boolean userExists(String username, String email) {

        return userRepository.findByUsername(username).isPresent()
                || userRepository.findByEmail(email).isPresent();
    }

}
