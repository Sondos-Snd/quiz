package com.interview.prep.service.impl;

import com.interview.prep.api.dto.PaymentDTO;
import com.interview.prep.api.mapper.PaymentMapper;
import com.interview.prep.core.NotFoundException;
import com.interview.prep.domain.Payment;
import com.interview.prep.domain.User;
import com.interview.prep.repo.PaymentRepo;
import com.interview.prep.repo.UserRepo;
import com.interview.prep.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo repo;
    private final UserRepo userRepo;

    @Override public PaymentDTO create(PaymentDTO dto) {
        User u = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User " + dto.getUserId() + " not found"));

        Payment e = Payment.builder()
                .user(u)
                .amountTotal(dto.getAmountTotal())
                .currency(dto.getCurrency())
                .breakdown(dto.getBreakdown() != null ? dto.getBreakdown() : "{}")
                .status(dto.getStatus())
                .build();

        return PaymentMapper.toDto(repo.save(e));
    }

    @Override public PaymentDTO get(Long id) {
        return repo.findById(id).map(PaymentMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Payment " + id + " not found"));
    }

    @Override public Page<PaymentDTO> list(Pageable p) {
        return repo.findAll(p).map(PaymentMapper::toDto);
    }

    @Override public PaymentDTO update(Long id, PaymentDTO dto) {
        Payment e = repo.findById(id).orElseThrow(() -> new NotFoundException("Payment " + id + " not found"));
        if (dto.getUserId() != null) {
            User u = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User " + dto.getUserId() + " not found"));
            e.setUser(u);
        }
        PaymentMapper.updateEntity(e, dto);
        return PaymentMapper.toDto(repo.save(e));
    }

    @Override public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Payment " + id + " not found");
        repo.deleteById(id);
    }
}
