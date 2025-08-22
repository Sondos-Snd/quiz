package com.interview.prep.service;

import com.interview.prep.api.dto.PaymentDTO;
import org.springframework.data.domain.*;

public interface PaymentService {
    PaymentDTO create(PaymentDTO dto);
    PaymentDTO get(Long id);
    Page<PaymentDTO> list(Pageable pageable);
    PaymentDTO update(Long id, PaymentDTO dto);
    void delete(Long id);
}
