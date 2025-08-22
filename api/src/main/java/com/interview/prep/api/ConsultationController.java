//package com.interview.prep.api;
//
//import com.interview.prep.api.dto.ConsultationDTO;
//import com.interview.prep.service.ConsultationService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.*;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController @RequestMapping("/api/v1/consultations")
//@RequiredArgsConstructor
//public class ConsultationController {
//    private final ConsultationService service;
//
//    @PostMapping public ResponseEntity<ConsultationDTO> create(@Valid @RequestBody ConsultationDTO dto) {
//        return ResponseEntity.ok(service.create(dto));
//    }
//
//    @GetMapping("/{id}") public ResponseEntity<ConsultationDTO> get(@PathVariable Long id) {
//        return ResponseEntity.ok(service.get(id));
//    }
//
//    @GetMapping public Page<ConsultationDTO> list(@PageableDefault(size = 20) Pageable p) {
//        return service.list(p);
//    }
//
//    @PutMapping("/{id}") public ResponseEntity<ConsultationDTO> update(@PathVariable Long id, @Valid @RequestBody ConsultationDTO dto) {
//        return ResponseEntity.ok(service.update(id, dto));
//    }
//
//    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
