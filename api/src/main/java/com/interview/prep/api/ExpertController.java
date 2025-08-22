//package com.interview.prep.api;
//
//import com.interview.prep.api.dto.ExpertDTO;
//import com.interview.prep.service.ExpertService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.*;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController @RequestMapping("/api/v1/experts")
//@RequiredArgsConstructor
//public class ExpertController {
//    private final ExpertService service;
//
//    @PostMapping public ResponseEntity<ExpertDTO> create(@Valid @RequestBody ExpertDTO dto) {
//        return ResponseEntity.ok(service.create(dto));
//    }
//
//    @GetMapping("/{id}") public ResponseEntity<ExpertDTO> get(@PathVariable Long id) {
//        return ResponseEntity.ok(service.get(id));
//    }
//
//    @GetMapping public Page<ExpertDTO> list(@PageableDefault(size = 20) Pageable p) {
//        return service.list(p);
//    }
//
//    @PutMapping("/{id}") public ResponseEntity<ExpertDTO> update(@PathVariable Long id, @Valid @RequestBody ExpertDTO dto) {
//        return ResponseEntity.ok(service.update(id, dto));
//    }
//
//    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
