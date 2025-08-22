//package com.interview.prep.service.impl;
//
//import com.interview.prep.api.dto.ExpertDTO;
//import com.interview.prep.api.mapper.ExpertMapper;
//import com.interview.prep.core.NotFoundException;
//import com.interview.prep.domain.Expert;
//import com.interview.prep.domain.User;
//import com.interview.prep.repo.ExpertRepo;
//import com.interview.prep.repo.UserRepo;
//import com.interview.prep.service.ExpertService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.*;
//import org.springframework.stereotype.Service;
//
//@Service @RequiredArgsConstructor
//public class ExpertServiceImpl implements ExpertService {
//    private final ExpertRepo repo;
//    private final UserRepo userRepo;
//
//    @Override public ExpertDTO create(ExpertDTO dto) {
//        User u = userRepo.findById(dto.getUserId())
//                .orElseThrow(() -> new NotFoundException("User " + dto.getUserId() + " not found"));
//
//        Expert e = Expert.builder()
//                .user(u)
//                .profession(dto.getProfession())
//                .regNumber(dto.getRegNumber())
//                .barOrdre(dto.getBarOrdre())
//                .specialties(dto.getSpecialties() != null ? dto.getSpecialties() : "[]")
//                .languages(dto.getLanguages() != null ? dto.getLanguages() : "[]")
//                .bio(dto.getBio())
//                .rateHourly(dto.getRateHourly())
//                .locations(dto.getLocations() != null ? dto.getLocations() : "[]")
//                .status(dto.getStatus())
//                .build();
//
//        return ExpertMapper.toDto(repo.save(e));
//    }
//
//    @Override public ExpertDTO get(Long id) {
//        return repo.findById(id).map(ExpertMapper::toDto)
//                .orElseThrow(() -> new NotFoundException("Expert " + id + " not found"));
//    }
//
//    @Override public Page<ExpertDTO> list(Pageable p) {
//        return repo.findAll(p).map(ExpertMapper::toDto);
//    }
//
//    @Override public ExpertDTO update(Long id, ExpertDTO dto) {
//        Expert e = repo.findById(id).orElseThrow(() -> new NotFoundException("Expert " + id + " not found"));
//        // handle user change
//        if (dto.getUserId() != null) {
//            User u = userRepo.findById(dto.getUserId())
//                    .orElseThrow(() -> new NotFoundException("User " + dto.getUserId() + " not found"));
//            e.setUser(u);
//        }
//        ExpertMapper.updateEntity(e, dto);
//        return ExpertMapper.toDto(repo.save(e));
//    }
//
//    @Override public void delete(Long id) {
//        if (!repo.existsById(id)) throw new NotFoundException("Expert " + id + " not found");
//        repo.deleteById(id);
//    }
//}
