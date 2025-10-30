package com.trajets.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArretCreateRequest {
    private String nom;
    private String localisation;
}
