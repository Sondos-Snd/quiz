package com.interview.prep.service.impl;

import com.interview.prep.api.dto.UserDTO;
import com.interview.prep.api.mapper.UserMapper;
import com.interview.prep.core.NotFoundException;
import com.interview.prep.domain.User;
import com.interview.prep.repo.UserRepo;
import com.interview.prep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo repo;

    @Override public UserDTO create(UserDTO dto) {
        User e = User.builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .locale(dto.getLocale() != null ? dto.getLocale() : "fr")
                .build();
        return UserMapper.toDto(repo.save(e));
    }

    @Override public UserDTO get(Long id) {
        return repo.findById(id).map(UserMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User " + id + " not found"));
    }

    @Override public Page<UserDTO> list(Pageable p) {
        return repo.findAll(p).map(UserMapper::toDto);
    }

    @Override public UserDTO update(Long id, UserDTO dto) {
        User e = repo.findById(id).orElseThrow(() -> new NotFoundException("User " + id + " not found"));
        UserMapper.updateEntity(e, dto);
        return UserMapper.toDto(repo.save(e));
    }

    @Override public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("User " + id + " not found");
        repo.deleteById(id);
    }
}
