package com.trajets.controller;

import jakarta.validation.Valid;
import com.trajets.dto.request.TrajetArretCreateRequest;
import com.trajets.dto.response.TrajetArretDTO;
import com.trajets.service.TrajetArretService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trajets/arrets")
@RequiredArgsConstructor
public class TrajetArretController {

    private final TrajetArretService trajetArretService;

    @GetMapping
    public List<TrajetArretDTO> getAll() {
        return trajetArretService.getAll();
    }

    @PostMapping
    public TrajetArretDTO create(@Valid @RequestBody TrajetArretCreateRequest request) {
        return trajetArretService.create(request);
    }

    @DeleteMapping
    public void delete(@RequestParam Long trajetId, @RequestParam Long arretId) {
        trajetArretService.delete(trajetId, arretId);
    }
}
