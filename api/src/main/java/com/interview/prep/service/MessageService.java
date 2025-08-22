package com.interview.prep.service;

import com.interview.prep.api.dto.MessageDTO;
import org.springframework.data.domain.*;

public interface MessageService {
    MessageDTO create(MessageDTO dto);
    MessageDTO get(Long id);
    Page<MessageDTO> list(Pageable pageable);
    MessageDTO update(Long id, MessageDTO dto);
    void delete(Long id);
}
