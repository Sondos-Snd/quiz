package com.interview.prep.service.impl;

import com.interview.prep.api.dto.MessageDTO;
import com.interview.prep.api.mapper.MessageMapper;
import com.interview.prep.core.NotFoundException;
import com.interview.prep.domain.Consultation;
import com.interview.prep.domain.Message;
import com.interview.prep.repo.ConsultationRepo;
import com.interview.prep.repo.MessageRepo;
import com.interview.prep.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepo repo;
    private final ConsultationRepo consultationRepo;

    @Override public MessageDTO create(MessageDTO dto) {
        Consultation c = consultationRepo.findById(dto.getConsultationId())
                .orElseThrow(() -> new NotFoundException("Consultation " + dto.getConsultationId() + " not found"));

        Message e = Message.builder()
                .consultation(c)
                .sender(dto.getSender())
                .body(dto.getBody())
                .attachments(dto.getAttachments() != null ? dto.getAttachments() : "[]")
                .build();

        return MessageMapper.toDto(repo.save(e));
    }

    @Override public MessageDTO get(Long id) {
        return repo.findById(id).map(MessageMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Message " + id + " not found"));
    }

    @Override public Page<MessageDTO> list(Pageable p) {
        return repo.findAll(p).map(MessageMapper::toDto);
    }

    @Override public MessageDTO update(Long id, MessageDTO dto) {
        Message e = repo.findById(id).orElseThrow(() -> new NotFoundException("Message " + id + " not found"));

        if (dto.getConsultationId() != null) {
            Consultation c = consultationRepo.findById(dto.getConsultationId())
                    .orElseThrow(() -> new NotFoundException("Consultation " + dto.getConsultationId() + " not found"));
            e.setConsultation(c);
        }

        MessageMapper.updateEntity(e, dto);
        return MessageMapper.toDto(repo.save(e));
    }

    @Override public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Message " + id + " not found");
        repo.deleteById(id);
    }
}
