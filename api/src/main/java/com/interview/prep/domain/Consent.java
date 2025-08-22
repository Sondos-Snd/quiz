package com.interview.prep.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "policy_version"}))
public class Consent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="user_id")
    private User user;

    @Column(name="policy_version", nullable=false)
    private String policyVersion;

    @Column(name="given_at", nullable=false)
    private OffsetDateTime givenAt = OffsetDateTime.now();

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object scope = "{}";
}
