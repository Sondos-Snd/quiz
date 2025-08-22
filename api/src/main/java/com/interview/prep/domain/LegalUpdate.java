package com.interview.prep.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class LegalUpdate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String source;
    @Column(nullable=false) private String title;
    @Column(nullable=false, columnDefinition="text") private String summary;
    @Column(nullable=false) private String link;

    @Column(name="effective_date") private LocalDate effectiveDate;

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object tags = "[]";

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
