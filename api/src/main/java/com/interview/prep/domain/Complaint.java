package com.interview.prep.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Complaint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name="raised_by")
    private User raisedBy;

    @Column(name="object_type", nullable=false) private String objectType;
    @Column(name="object_id",   nullable=false) private Long objectId;

    @Column(nullable=false) private String status;
    @Column(name="resolution_note", columnDefinition="text") private String resolutionNote;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
