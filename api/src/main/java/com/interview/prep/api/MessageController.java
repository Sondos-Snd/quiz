package com.interview.prep.api;

import com.interview.prep.api.dto.MessageDTO;
import com.interview.prep.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService service;

    @PostMapping public ResponseEntity<MessageDTO> create(@Valid @RequestBody MessageDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}") public ResponseEntity<MessageDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping public Page<MessageDTO> list(@PageableDefault(size = 20) Pageable p) {
        return service.list(p);
    }

    @PutMapping("/{id}") public ResponseEntity<MessageDTO> update(@PathVariable Long id, @Valid @RequestBody MessageDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
