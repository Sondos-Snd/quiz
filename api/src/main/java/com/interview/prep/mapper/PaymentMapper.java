package com.interview.prep.api.mapper;

import com.interview.prep.api.dto.PaymentDTO;
import com.interview.prep.domain.Payment;

public final class PaymentMapper {
    private PaymentMapper() {}

    public static PaymentDTO toDto(Payment e) {
        if (e == null) return null;
        return PaymentDTO.builder()
                .id(e.getId())
                .userId(e.getUser() != null ? e.getUser().getId() : null)
                .amountTotal(e.getAmountTotal())
                .currency(e.getCurrency())
                .breakdown(e.getBreakdown())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public static void updateEntity(Payment e, PaymentDTO d) {
        if (d.getAmountTotal() != null) e.setAmountTotal(d.getAmountTotal());
        if (d.getCurrency() != null) e.setCurrency(d.getCurrency());
        if (d.getBreakdown() != null) e.setBreakdown(d.getBreakdown());
        if (d.getStatus() != null) e.setStatus(d.getStatus());
        // userId handled by service
    }
}
