package com.trajets.controller;

import jakarta.validation.Valid;
import com.trajets.dto.request.TrajetCreateRequest;
import com.trajets.dto.response.TrajetDTO;
import com.trajets.service.TrajetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trajets")
@RequiredArgsConstructor
public class TrajetController {

    private final TrajetService trajetService;

    @GetMapping
    public List<TrajetDTO> getAll() {
        return trajetService.getAllTrajets();
    }

    @GetMapping("/{id}")
    public TrajetDTO getById(@PathVariable Long id) {
        return trajetService.getTrajetById(id);
    }

    @PostMapping
    public TrajetDTO create(@Valid @RequestBody TrajetCreateRequest request) {
        return trajetService.createTrajet(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        trajetService.deleteTrajet(id);
    }
}
