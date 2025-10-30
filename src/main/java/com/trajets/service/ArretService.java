package com.trajets.service;

import com.trajets.dto.request.ArretCreateRequest;
import com.trajets.dto.response.ArretDTO;

import java.util.List;

public interface ArretService {
    List<ArretDTO> getAllArrets();
    ArretDTO getArretById(Long id);
    ArretDTO createArret(ArretCreateRequest request);
    void deleteArret(Long id);
}
