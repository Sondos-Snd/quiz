package com.interview.prep.service;

import com.interview.prep.api.dto.ExpertDTO;
import org.springframework.data.domain.*;

public interface ExpertService {
    ExpertDTO create(ExpertDTO dto);
    ExpertDTO get(Long id);
    Page<ExpertDTO> list(Pageable pageable);
    ExpertDTO update(Long id, ExpertDTO dto);
    void delete(Long id);
}
