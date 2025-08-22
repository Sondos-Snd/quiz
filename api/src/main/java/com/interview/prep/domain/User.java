package com.interview.prep.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLCITextType;
import org.hibernate.annotations.Type;

@Entity
@Table(name="\"user\"")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Map Postgres CITEXT properly
    @Type(PostgreSQLCITextType.class)
    @Column(columnDefinition = "citext", nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String locale = "fr";

    @Column(name="created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
