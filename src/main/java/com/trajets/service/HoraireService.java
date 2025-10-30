package com.trajets.service;

import com.trajets.dto.request.HoraireCreateRequest;
import com.trajets.dto.response.HoraireDTO;
import java.util.List;

public interface HoraireService {
    List<HoraireDTO> getAllHoraires();
    HoraireDTO getHoraireById(Long id);
    HoraireDTO createHoraire(HoraireCreateRequest request);
    void deleteHoraire(Long id);
}
