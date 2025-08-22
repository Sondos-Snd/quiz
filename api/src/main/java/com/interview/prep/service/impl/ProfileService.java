// src/main/java/com/interview/prep/web/ProfileService.java
package com.interview.prep.service.impl;

import com.interview.prep.domain.Consent;
import com.interview.prep.domain.User;
import com.interview.prep.repo.ConsentRepo;
import com.interview.prep.repo.UserRepo;
import com.interview.prep.dto.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Map;

@Service
public class ProfileService {
    private final UserRepo users; private final ConsentRepo consents;
    public ProfileService(UserRepo users, ConsentRepo consents){ this.users = users; this.consents = consents; }

    private String jwtEmail(Jwt jwt){
        String email = jwt.getClaimAsString("email");
        if (email == null || email.isBlank())
            email = jwt.getClaimAsString("preferred_username");
        return email;
    }
    private String jwtName(Jwt jwt){
        String name = jwt.getClaimAsString("name");
        if (name == null || name.isBlank()) {
            String gn = jwt.getClaimAsString("given_name"); String fn = jwt.getClaimAsString("family_name");
            name = ((gn!=null?gn:"") + " " + (fn!=null?fn:"")).trim();
        }
        return (name==null || name.isBlank()) ? "User" : name;
    }

    @Transactional
    public ProfileDTO get(Jwt jwt){
        String email = jwtEmail(jwt);
        User u = users.findByEmailIgnoreCase(email)
                .orElseGet(() -> users.save(User.builder().email(email).locale("fr").build()));
        var cs = consents.findByUserId(u.getId()).stream().map(c ->
                new ConsentDTO(c.getId(), c.getPolicyVersion(), c.getGivenAt(), (Map)c.getScope())
        ).toList();
        return new ProfileDTO(jwtName(jwt), u.getEmail(), u.getPhone(), u.getLocale(), cs);
    }

    @Transactional
    public ProfileDTO update(Jwt jwt, UpdateProfileDTO in){
        String email = jwtEmail(jwt);
        User u = users.findByEmailIgnoreCase(email)
                .orElseThrow(); // created on GET or first call

        if (in.phone()!=null)  u.setPhone(in.phone());
        if (in.locale()!=null) u.setLocale(in.locale());

        // Simplest policy: replace all consents with provided list
        consents.deleteAll(consents.findByUserId(u.getId()));
        if (in.consents()!=null){
            for (var c : in.consents()){
                var entity = Consent.builder()
                        .user(u)
                        .policyVersion(c.policyVersion())
                        .givenAt(OffsetDateTime.now())
                        .scope(c.scope()==null ? Map.of() : c.scope())
                        .build();
                consents.save(entity);
            }
        }
        users.save(u);
        return get(jwt);
    }
}
