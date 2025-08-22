package com.interview.prep.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentDTO {
    private Long id;

    @NotNull private Long userId;

    @NotNull @Digits(integer=12, fraction=2)
    private BigDecimal amountTotal;

    @NotBlank private String currency;

    private Object breakdown;       // jsonb with {platform_fee, taxes}
    @NotBlank private String status;

    private OffsetDateTime createdAt;
}
