package com.trajets.service.impl;

import com.trajets.dto.request.TrajetArretCreateRequest;
import com.trajets.dto.response.TrajetArretDTO;
import com.trajets.model.Arret;
import com.trajets.model.Trajet;
import com.trajets.model.TrajetArret;
import com.trajets.model.TrajetArretId;
import com.trajets.repository.ArretRepository;
import com.trajets.repository.TrajetArretRepository;
import com.trajets.repository.TrajetRepository;
import com.trajets.service.TrajetArretService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrajetArretServiceImpl implements TrajetArretService {

    private final TrajetArretRepository trajetArretRepository;
    private final TrajetRepository trajetRepository;
    private final ArretRepository arretRepository;

    @Override
    public List<TrajetArretDTO> getAll() {
        return trajetArretRepository.findAll().stream()
                .map(ta -> new TrajetArretDTO(
                        ta.getTrajet().getId(),
                        ta.getTrajet().getNom(),
                        ta.getArret().getId(),
                        ta.getArret().getNom(),
                        ta.getOrdre()))
                .collect(Collectors.toList());
    }

    @Override
    public TrajetArretDTO create(TrajetArretCreateRequest request) {
        Trajet trajet = trajetRepository.findById(request.getTrajetId())
                .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));
        Arret arret = arretRepository.findById(request.getArretId())
                .orElseThrow(() -> new RuntimeException("Arrêt non trouvé"));

        TrajetArret ta = new TrajetArret();
        ta.setId(new TrajetArretId(trajet.getId(), arret.getId()));
        ta.setTrajet(trajet);
        ta.setArret(arret);
        ta.setOrdre(request.getOrdre());

        trajetArretRepository.save(ta);
        return new TrajetArretDTO(trajet.getId(), trajet.getNom(), arret.getId(), arret.getNom(), ta.getOrdre());
    }

    @Override
    public void delete(Long trajetId, Long arretId) {
        trajetArretRepository.deleteById(new TrajetArretId(trajetId, arretId));
    }
}
