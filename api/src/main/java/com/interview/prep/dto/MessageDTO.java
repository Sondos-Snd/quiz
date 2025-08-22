package com.interview.prep.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.OffsetDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageDTO {
    private Long id;

    @NotNull private Long consultationId;


    @NotBlank private String body;

    private Object attachments;     // jsonb
    private OffsetDateTime createdAt;
}
