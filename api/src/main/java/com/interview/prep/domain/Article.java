package com.interview.prep.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String title;
    @Column(nullable=false, unique=true) private String slug;
    @Column(nullable=false, columnDefinition="text") private String body;

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object tags = "[]";

    @ManyToOne(optional=false) @JoinColumn(name="author_id")
    private User author;

    @ManyToOne @JoinColumn(name="reviewed_by_id")
    private User reviewedBy;

    @ManyToOne @JoinColumn(name="disclaimer_id")
    private Disclaimer disclaimer;

    @Column(name="published_at") private OffsetDateTime publishedAt;
    @Column(name="created_at", nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();
}
