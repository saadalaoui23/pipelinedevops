package com.trajets.service;

import com.trajets.dto.request.HoraireArretCreateRequest;
import com.trajets.dto.response.HoraireArretDTO;
import java.util.List;

public interface HoraireArretService {
    List<HoraireArretDTO> getAll();
    HoraireArretDTO create(HoraireArretCreateRequest request);
    void delete(Long horaireId, Long arretId);
}
