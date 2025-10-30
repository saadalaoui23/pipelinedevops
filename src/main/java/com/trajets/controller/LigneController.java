package com.trajets.controller;


import com.trajets.model.Ligne;
import com.trajets.repository.LigneRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lignes")
public class LigneController {

    private final LigneRepository ligneRepository;

    public LigneController(LigneRepository ligneRepository) {
        this.ligneRepository = ligneRepository;
    }

    @GetMapping
    public List<Ligne> getAll() {
        return ligneRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ligne> getById(@PathVariable Long id) {
        return ligneRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ligne> create(@RequestBody Ligne ligne) {
        if (ligneRepository.existsByCode(ligne.getCode())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ligneRepository.save(ligne));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ligne> update(@PathVariable Long id, @RequestBody Ligne updated) {
        return ligneRepository.findById(id)
                .map(ligne -> {
                    ligne.setCode(updated.getCode());
                    ligne.setNom(updated.getNom());
                    ligne.setDescription(updated.getDescription());
                    ligneRepository.save(ligne);
                    return ResponseEntity.ok(ligne);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!ligneRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ligneRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

