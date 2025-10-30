package com.trajets.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrajetArretCreateRequest {
    private Long trajetId;
    private Long arretId;
    private int ordre;
}
