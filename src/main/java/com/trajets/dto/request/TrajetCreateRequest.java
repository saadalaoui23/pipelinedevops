package com.trajets.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrajetCreateRequest {
    private String nom;
    private Long ligneId;
}
