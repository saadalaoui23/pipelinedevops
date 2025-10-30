package com.trajets.service;

import com.trajets.dto.request.TrajetArretCreateRequest;
import com.trajets.dto.response.TrajetArretDTO;
import java.util.List;

public interface TrajetArretService {
    List<TrajetArretDTO> getAll();
    TrajetArretDTO create(TrajetArretCreateRequest request);
    void delete(Long trajetId, Long arretId);
}
