package com.interview.prep.api.mapper;

import com.interview.prep.api.dto.UserDTO;
import com.interview.prep.domain.User;

public final class UserMapper {
    private UserMapper() {}

    public static UserDTO toDto(User e) {
        if (e == null) return null;
        return UserDTO.builder()
                .id(e.getId())
                .email(e.getEmail())
                .phone(e.getPhone())
                .locale(e.getLocale())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public static void updateEntity(User e, UserDTO d) {
        if (d.getEmail() != null) e.setEmail(d.getEmail());
        if (d.getPhone() != null) e.setPhone(d.getPhone());
        if (d.getLocale() != null) e.setLocale(d.getLocale());
    }
}
