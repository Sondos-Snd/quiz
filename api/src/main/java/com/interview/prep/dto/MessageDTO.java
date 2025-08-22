package com.interview.prep.api.dto;

import com.interview.prep.domain.Enums.Sender;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.OffsetDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageDTO {
    private Long id;

    @NotNull private Long consultationId;

    @NotNull private Sender sender;

    @NotBlank private String body;

    private Object attachments;     // jsonb
    private OffsetDateTime createdAt;
}
