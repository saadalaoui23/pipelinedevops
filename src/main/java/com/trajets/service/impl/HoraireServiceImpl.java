package com.trajets.service.impl;

import com.trajets.dto.request.HoraireCreateRequest;
import com.trajets.dto.response.HoraireDTO;
import com.trajets.model.Horaire;
import com.trajets.model.Trajet;
import com.trajets.repository.HoraireRepository;
import com.trajets.repository.TrajetRepository;
import com.trajets.service.HoraireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HoraireServiceImpl implements HoraireService {

    private final HoraireRepository horaireRepository;
    private final TrajetRepository trajetRepository;

    @Override
    public List<HoraireDTO> getAllHoraires() {
        return horaireRepository.findAll().stream()
                .map(h -> new HoraireDTO(
                        h.getId(),
                        h.getHeureDepart(),
                        h.getHeureArrivee(),
                        h.getTrajet().getId(),
                        h.getTrajet().getNom()))
                .collect(Collectors.toList());
    }

    @Override
    public HoraireDTO getHoraireById(Long id) {
        Horaire horaire = horaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horaire non trouvé"));
        return new HoraireDTO(
                horaire.getId(),
                horaire.getHeureDepart(),
                horaire.getHeureArrivee(),
                horaire.getTrajet().getId(),
                horaire.getTrajet().getNom());
    }

    @Override
    public HoraireDTO createHoraire(HoraireCreateRequest request) {
        Trajet trajet = trajetRepository.findById(request.getTrajetId())
                .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));

        Horaire horaire = new Horaire();
        horaire.setHeureDepart(request.getHeureDepart());
        horaire.setHeureArrivee(request.getHeureArrivee());
        horaire.setTrajet(trajet);

        Horaire saved = horaireRepository.save(horaire);
        return new HoraireDTO(
                saved.getId(),
                saved.getHeureDepart(),
                saved.getHeureArrivee(),
                trajet.getId(),
                trajet.getNom());
    }

    @Override
    public void deleteHoraire(Long id) {
        horaireRepository.deleteById(id);
    }
}
