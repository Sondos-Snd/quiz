package com.interview.prep.api.mapper;

import com.interview.prep.api.dto.MessageDTO;
import com.interview.prep.domain.Message;

public final class MessageMapper {
    private MessageMapper() {}

    public static MessageDTO toDto(Message e) {
        if (e == null) return null;
        return MessageDTO.builder()
                .id(e.getId())
                .consultationId(e.getConsultation() != null ? e.getConsultation().getId() : null)
                .sender(e.getSender())
                .body(e.getBody())
                .attachments(e.getAttachments())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public static void updateEntity(Message e, MessageDTO d) {
        if (d.getSender() != null) e.setSender(d.getSender());
        if (d.getBody() != null) e.setBody(d.getBody());
        if (d.getAttachments() != null) e.setAttachments(d.getAttachments());
        // consultationId handled by service
    }
}
