package com.interview.prep.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="issued_by", nullable=false)
    private String issuedBy = "platform";

    @ManyToOne(optional=false) @JoinColumn(name="issued_to")
    private User issuedTo;

    @Type(JsonType.class) @Column(columnDefinition="jsonb", nullable=false)
    private Object lines = "[]";

    @Column(precision=12, scale=2, nullable=false) private BigDecimal total;
    @Column(nullable=false) private String currency;
    @Column(name="pdf_url") private String pdfUrl;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
