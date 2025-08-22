package com.interview.prep.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name="user_id")
    private User user;

    @Column(name="amount_total", precision=12, scale=2, nullable=false)
    private BigDecimal amountTotal;

    @Column(nullable=false) private String currency;

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object breakdown = "{}"; // {platform_fee, taxes}

    @Column(nullable=false) private String status;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
