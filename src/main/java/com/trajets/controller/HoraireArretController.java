package com.trajets.controller;

import jakarta.validation.Valid;
import com.trajets.dto.request.HoraireArretCreateRequest;
import com.trajets.dto.response.HoraireArretDTO;
import com.trajets.service.HoraireArretService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horaires/arrets")
@RequiredArgsConstructor
public class HoraireArretController {

    private final HoraireArretService horaireArretService;

    @GetMapping
    public List<HoraireArretDTO> getAll() {
        return horaireArretService.getAll();
    }

    @PostMapping
    public HoraireArretDTO create(@Valid @RequestBody HoraireArretCreateRequest request) {
        return horaireArretService.create(request);
    }

    @DeleteMapping
    public void delete(@RequestParam Long horaireId, @RequestParam Long arretId) {
        horaireArretService.delete(horaireId, arretId);
    }
}
