package com.interview.prep.service.impl;

import com.interview.prep.api.dto.ConsultationDTO;
import com.interview.prep.api.mapper.ConsultationMapper;
import com.interview.prep.core.NotFoundException;
import com.interview.prep.domain.Consultation;
import com.interview.prep.domain.Expert;
import com.interview.prep.domain.User;
import com.interview.prep.repo.ConsultationRepo;
import com.interview.prep.repo.ExpertRepo;
import com.interview.prep.repo.UserRepo;
import com.interview.prep.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepo repo;
    private final UserRepo userRepo;
    private final ExpertRepo expertRepo;

    @Override public ConsultationDTO create(ConsultationDTO dto) {
        User u = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User " + dto.getUserId() + " not found"));
        Expert ex = expertRepo.findById(dto.getExpertId())
                .orElseThrow(() -> new NotFoundException("Expert " + dto.getExpertId() + " not found"));

        Consultation e = Consultation.builder()
                .user(u)
                .expert(ex)
                .status(dto.getStatus())
                .intent(dto.getIntent())
                .scheduledAt(dto.getScheduledAt())
                .channel(dto.getChannel())
                .meta(dto.getMeta() != null ? dto.getMeta() : "{}")
                .build();

        return ConsultationMapper.toDto(repo.save(e));
    }

    @Override public ConsultationDTO get(Long id) {
        return repo.findById(id).map(ConsultationMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Consultation " + id + " not found"));
    }

    @Override public Page<ConsultationDTO> list(Pageable p) {
        return repo.findAll(p).map(ConsultationMapper::toDto);
    }

    @Override public ConsultationDTO update(Long id, ConsultationDTO dto) {
        Consultation e = repo.findById(id).orElseThrow(() -> new NotFoundException("Consultation " + id + " not found"));

        if (dto.getUserId() != null) {
            User u = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User " + dto.getUserId() + " not found"));
            e.setUser(u);
        }
        if (dto.getExpertId() != null) {
            Expert ex = expertRepo.findById(dto.getExpertId())
                    .orElseThrow(() -> new NotFoundException("Expert " + dto.getExpertId() + " not found"));
            e.setExpert(ex);
        }

        ConsultationMapper.updateEntity(e, dto);
        return ConsultationMapper.toDto(repo.save(e));
    }

    @Override public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Consultation " + id + " not found");
        repo.deleteById(id);
    }
}
