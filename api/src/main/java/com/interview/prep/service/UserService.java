package com.interview.prep.service;

import com.interview.prep.api.dto.UserDTO;
import org.springframework.data.domain.*;

public interface UserService {
    UserDTO create(UserDTO dto);
    UserDTO get(Long id);
    Page<UserDTO> list(Pageable pageable);
    UserDTO update(Long id, UserDTO dto);   // full or partial
    void delete(Long id);
}
