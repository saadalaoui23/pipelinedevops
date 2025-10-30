package com.trajets.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
public class HoraireCreateRequest {
    private LocalTime heureDepart;
    private LocalTime heureArrivee;
    private Long trajetId;
}
