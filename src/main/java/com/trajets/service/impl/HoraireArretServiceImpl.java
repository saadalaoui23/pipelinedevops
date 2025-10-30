package com.trajets.service.impl;

import com.trajets.dto.request.HoraireArretCreateRequest;
import com.trajets.dto.response.HoraireArretDTO;
import com.trajets.model.*;
import com.trajets.repository.*;
import com.trajets.service.HoraireArretService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HoraireArretServiceImpl implements HoraireArretService {

    private final HoraireArretRepository horaireArretRepository;
    private final HoraireRepository horaireRepository;
    private final ArretRepository arretRepository;

    @Override
    public List<HoraireArretDTO> getAll() {
        return horaireArretRepository.findAll().stream()
                .map(ha -> new HoraireArretDTO(
                        ha.getHoraire().getId(),
                        ha.getArret().getId(),
                        ha.getArret().getNom(),
                        ha.getHeurePassage()))
                .collect(Collectors.toList());
    }

    @Override
    public HoraireArretDTO create(HoraireArretCreateRequest request) {
        Horaire horaire = horaireRepository.findById(request.getHoraireId())
                .orElseThrow(() -> new RuntimeException("Horaire non trouvé"));
        Arret arret = arretRepository.findById(request.getArretId())
                .orElseThrow(() -> new RuntimeException("Arrêt non trouvé"));

        HoraireArret ha = new HoraireArret();
        ha.setId(new HoraireArretId(horaire.getId(), arret.getId()));
        ha.setHoraire(horaire);
        ha.setArret(arret);
        ha.setHeurePassage(request.getHeurePassage());

        horaireArretRepository.save(ha);
        return new HoraireArretDTO(horaire.getId(), arret.getId(), arret.getNom(), ha.getHeurePassage());
    }

    @Override
    public void delete(Long horaireId, Long arretId) {
        horaireArretRepository.deleteById(new HoraireArretId(horaireId, arretId));
    }
}
