// src/main/java/org/madhawaa/dto/requestDTO/FileUploadRequest.java
package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FileUploadRequestDTO {
    @NotBlank
    private String fileName;

    @NotBlank
    private String base64;   // the file’s contents, no “data:…” prefix
}
