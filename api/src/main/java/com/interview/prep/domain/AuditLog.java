package com.interview.prep.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String who;
    @Column(nullable=false) private String action;
    @Column(nullable=false) private String entity;

    @Column(name="at", nullable=false)
    private OffsetDateTime at = OffsetDateTime.now();

    private String ip; // store as text; map to INET via native if needed
}
