// src/main/java/com/interview/prep/web/dto/ProfileDTO.java
package com.interview.prep.dto;
import java.util.List;
public record ProfileDTO(
        String name,        // from Keycloak token
        String email,       // from Keycloak token / db
        String phone,       // from DB
        String locale,      // from DB
        List<ConsentDTO> consents // from DB
) {}
