package com.interview.prep.domain;

import com.interview.prep.domain.Enums.*;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.math.BigDecimal;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Expert {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Profession profession;

    @Column(name="reg_number") private String regNumber;
    @Column(name="bar_ordre") private String barOrdre;

    @Type(JsonType.class) @Column(columnDefinition = "jsonb", nullable = false)
    private Object specialties = "[]";

    @Type(JsonType.class) @Column(columnDefinition = "jsonb", nullable = false)
    private Object languages = "[]";

    private String bio;

    @Column(name="rate_hourly", precision=10, scale=2)
    private BigDecimal rateHourly;

    @Type(JsonType.class) @Column(columnDefinition = "jsonb", nullable = false)
    private Object locations = "[]";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpertStatus status = ExpertStatus.pending;
}
