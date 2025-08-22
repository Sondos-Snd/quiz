package com.interview.prep.api.mapper;

import com.interview.prep.api.dto.ExpertDTO;
import com.interview.prep.domain.Expert;

public final class ExpertMapper {
    private ExpertMapper() {}

    public static ExpertDTO toDto(Expert e) {
        if (e == null) return null;
        return ExpertDTO.builder()
                .id(e.getId())
                .userId(e.getUser() != null ? e.getUser().getId() : null)
                .profession(e.getProfession())
                .regNumber(e.getRegNumber())
                .barOrdre(e.getBarOrdre())
                .specialties(e.getSpecialties())
                .languages(e.getLanguages())
                .bio(e.getBio())
                .rateHourly(e.getRateHourly())
                .locations(e.getLocations())
                .status(e.getStatus())
                .build();
    }

    public static void updateEntity(Expert e, ExpertDTO d) {
        if (d.getProfession() != null) e.setProfession(d.getProfession());
        if (d.getRegNumber() != null) e.setRegNumber(d.getRegNumber());
        if (d.getBarOrdre() != null) e.setBarOrdre(d.getBarOrdre());
        if (d.getSpecialties() != null) e.setSpecialties(d.getSpecialties());
        if (d.getLanguages() != null) e.setLanguages(d.getLanguages());
        if (d.getBio() != null) e.setBio(d.getBio());
        if (d.getRateHourly() != null) e.setRateHourly(d.getRateHourly());
        if (d.getLocations() != null) e.setLocations(d.getLocations());
        if (d.getStatus() != null) e.setStatus(d.getStatus());
        // userId is handled by service (DB lookup)
    }
}
