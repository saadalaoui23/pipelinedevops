package com.trajets.service;

import com.trajets.dto.request.TrajetCreateRequest;
import com.trajets.dto.response.TrajetDTO;
import java.util.List;

public interface TrajetService {
    List<TrajetDTO> getAllTrajets();
    TrajetDTO getTrajetById(Long id);
    TrajetDTO createTrajet(TrajetCreateRequest request);
    void deleteTrajet(Long id);
}
