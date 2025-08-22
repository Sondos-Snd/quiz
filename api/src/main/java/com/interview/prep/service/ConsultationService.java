package com.interview.prep.service;

import com.interview.prep.api.dto.ConsultationDTO;
import org.springframework.data.domain.*;

public interface ConsultationService {
    ConsultationDTO create(ConsultationDTO dto);
    ConsultationDTO get(Long id);
    Page<ConsultationDTO> list(Pageable pageable);
    ConsultationDTO update(Long id, ConsultationDTO dto);
    void delete(Long id);
}
