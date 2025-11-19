package com.trajets.controller;

import jakarta.validation.Valid;
import com.trajets.dto.request.ArretCreateRequest;
import com.trajets.dto.response.ArretDTO;
import com.trajets.service.ArretService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arrets")
@RequiredArgsConstructor
public class ArretController {

    private final ArretService arretService;

    @GetMapping
    public List<ArretDTO> getAll() {
        return arretService.getAllArrets();
    }

    @GetMapping("/{id}")
    public ArretDTO getById(@PathVariable Long id) {
        return arretService.getArretById(id);
    }

    @PostMapping
    public ArretDTO create(@Valid @RequestBody ArretCreateRequest request) {
        return arretService.createArret(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        arretService.deleteArret(id);
    }
}
