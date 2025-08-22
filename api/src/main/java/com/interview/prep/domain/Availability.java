package com.interview.prep.domain;

import com.interview.prep.domain.Enums.Channel;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Availability {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name="expert_id")
    private Expert expert;

    @Column(name="start", nullable = false)
    private OffsetDateTime start;

    @Column(name="end", nullable = false)
    private OffsetDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Channel channel;
}
