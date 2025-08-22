package com.interview.prep.api.mapper;

import com.interview.prep.api.dto.ConsultationDTO;
import com.interview.prep.domain.Consultation;

public final class ConsultationMapper {
    private ConsultationMapper() {}

    public static ConsultationDTO toDto(Consultation e) {
        if (e == null) return null;
        return ConsultationDTO.builder()
                .id(e.getId())
                .userId(e.getUser() != null ? e.getUser().getId() : null)
                .expertId(e.getExpert() != null ? e.getExpert().getId() : null)
                .status(e.getStatus())
                .intent(e.getIntent())
                .scheduledAt(e.getScheduledAt())
                .channel(e.getChannel())
                .meta(e.getMeta())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public static void updateEntity(Consultation e, ConsultationDTO d) {
        if (d.getStatus() != null) e.setStatus(d.getStatus());
        if (d.getIntent() != null) e.setIntent(d.getIntent());
        if (d.getScheduledAt() != null) e.setScheduledAt(d.getScheduledAt());
        if (d.getChannel() != null) e.setChannel(d.getChannel());
        if (d.getMeta() != null) e.setMeta(d.getMeta());
        // userId/expertId handled by service
    }
}
