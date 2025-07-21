package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttendancePasswordRequestDTO {

    @NotBlank
    private String password;
}
