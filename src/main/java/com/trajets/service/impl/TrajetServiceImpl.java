package com.trajets.service.impl;

import com.trajets.dto.request.TrajetCreateRequest;
import com.trajets.dto.response.TrajetDTO;
import com.trajets.model.Ligne;
import com.trajets.model.Trajet;
import com.trajets.repository.LigneRepository;
import com.trajets.repository.TrajetRepository;
import com.trajets.service.TrajetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrajetServiceImpl implements TrajetService {

    private final TrajetRepository trajetRepository;
    private final LigneRepository ligneRepository;

    @Override
    public List<TrajetDTO> getAllTrajets() {
        return trajetRepository.findAll().stream()
                .map(t -> new TrajetDTO(
                        t.getId(),
                        t.getNom(),
                        t.getLigne().getId(),
                        t.getLigne().getNom()))
                .collect(Collectors.toList());
    }

    @Override
    public TrajetDTO getTrajetById(Long id) {
        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));
        return new TrajetDTO(
                trajet.getId(),
                trajet.getNom(),
                trajet.getLigne().getId(),
                trajet.getLigne().getNom());
    }

    @Override
    public TrajetDTO createTrajet(TrajetCreateRequest request) {
        Ligne ligne = ligneRepository.findById(request.getLigneId())
                .orElseThrow(() -> new RuntimeException("Ligne non trouvée"));

        Trajet trajet = new Trajet();
        trajet.setNom(request.getNom());
        trajet.setLigne(ligne);

        Trajet saved = trajetRepository.save(trajet);
        return new TrajetDTO(saved.getId(), saved.getNom(), ligne.getId(), ligne.getNom());
    }

    @Override
    public void deleteTrajet(Long id) {
        trajetRepository.deleteById(id);
    }
}
