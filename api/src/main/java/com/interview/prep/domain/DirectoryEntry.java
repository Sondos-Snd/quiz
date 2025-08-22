package com.interview.prep.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DirectoryEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String type;
    @Column(nullable=false) private String name;
    private String address;

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object phones = "[]";

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object geo = "{}";

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object openingHours = "[]";

    @Column(nullable=false) private boolean verified = false;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
