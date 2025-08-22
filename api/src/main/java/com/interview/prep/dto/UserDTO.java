package com.interview.prep.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.OffsetDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private Long id;

    @NotBlank @Email
    private String email;

    private String phone;

    @NotBlank
    private String locale;

    private OffsetDateTime createdAt;
}
