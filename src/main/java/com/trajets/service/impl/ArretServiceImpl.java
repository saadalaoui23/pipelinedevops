package com.trajets.service.impl;

import com.trajets.dto.request.ArretCreateRequest;
import com.trajets.dto.response.ArretDTO;
import com.trajets.model.Arret;
import com.trajets.repository.ArretRepository;
import com.trajets.service.ArretService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArretServiceImpl implements ArretService {

    private final ArretRepository arretRepository;

    @Override
    public List<ArretDTO> getAllArrets() {
        return arretRepository.findAll().stream()
                .map(a -> new ArretDTO(a.getId(), a.getNom(), a.getLocalisation()))
                .collect(Collectors.toList());
    }

    @Override
    public ArretDTO getArretById(Long id) {
        Arret arret = arretRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arret non trouvé"));
        return new ArretDTO(arret.getId(), arret.getNom(), arret.getLocalisation());
    }

    @Override
    public ArretDTO createArret(ArretCreateRequest request) {
        Arret arret = new Arret();
        arret.setNom(request.getNom());
        arret.setLocalisation(request.getLocalisation());
        Arret saved = arretRepository.save(arret);
        return new ArretDTO(saved.getId(), saved.getNom(), saved.getLocalisation());
    }

    @Override
    public void deleteArret(Long id) {
        arretRepository.deleteById(id);
    }
}
