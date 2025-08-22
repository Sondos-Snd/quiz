package com.interview.prep.api;

import com.interview.prep.dto.ProfileDTO;
import com.interview.prep.dto.UpdateProfileDTO;
import com.interview.prep.service.impl.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MeController {
    private final ProfileService svc;
    public MeController(ProfileService svc){ this.svc = svc; }

    @GetMapping("/me") @PreAuthorize("hasRole('USER')")
    public String me(@AuthenticationPrincipal Jwt jwt){

        return "success";

    }

    @PutMapping("/me") @PreAuthorize("isAuthenticated()")
    public ProfileDTO update(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdateProfileDTO body){ return svc.update(jwt, body); }
}
