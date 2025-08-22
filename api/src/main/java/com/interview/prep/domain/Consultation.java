package com.interview.prep.domain;

import com.interview.prep.domain.Enums.Channel;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Consultation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(optional = false) @JoinColumn(name="expert_id")
    private Expert expert;

    @Column(nullable = false)
    private String status;   // requested/confirmed/completed/cancelled

    @Column(nullable = false, columnDefinition = "text")
    private String intent;

    @Column(name="scheduled_at") private OffsetDateTime scheduledAt;

    @Enumerated(EnumType.STRING) private Channel channel;

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object meta = "{}";

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
