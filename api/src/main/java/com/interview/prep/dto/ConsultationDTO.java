package com.interview.prep.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.OffsetDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ConsultationDTO {
    private Long id;

    @NotNull private Long userId;
    @NotNull private Long expertId;

    @NotBlank private String status;
    @NotBlank private String intent;

    private OffsetDateTime scheduledAt;

    private Object meta;            // jsonb
    private OffsetDateTime createdAt;
}
