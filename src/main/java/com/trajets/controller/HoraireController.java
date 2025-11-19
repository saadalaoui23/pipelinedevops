package com.trajets.controller;

import jakarta.validation.Valid;
import com.trajets.dto.request.HoraireCreateRequest;
import com.trajets.dto.response.HoraireDTO;
import com.trajets.service.HoraireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horaires")
@RequiredArgsConstructor
public class HoraireController {

    private final HoraireService horaireService;

    @GetMapping
    public List<HoraireDTO> getAll() {
        return horaireService.getAllHoraires();
    }

    @GetMapping("/{id}")
    public HoraireDTO getById(@PathVariable Long id) {
        return horaireService.getHoraireById(id);
    }

    @PostMapping
    public HoraireDTO create(@Valid @RequestBody HoraireCreateRequest request) {
        return horaireService.createHoraire(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        horaireService.deleteHoraire(id);
    }
}
