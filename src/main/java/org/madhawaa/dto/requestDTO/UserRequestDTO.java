package org.madhawaa.dto.requestDTO;
import jakarta.validation.constraints.*;
import org.madhawaa.enums.Role;
import lombok.Data;


@Data
public class UserRequestDTO {

    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String username;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Size(max = 20)
    private String phone;

    @NotNull
    private Role role;


}
