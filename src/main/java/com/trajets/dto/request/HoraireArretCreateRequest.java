package com.trajets.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
public class HoraireArretCreateRequest {
    private Long horaireId;
    private Long arretId;
    private LocalTime heurePassage;
}
