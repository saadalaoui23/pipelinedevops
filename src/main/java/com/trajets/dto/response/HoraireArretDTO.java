package com.trajets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoraireArretDTO {
    private Long horaireId;
    private Long arretId;
    private String arretNom;
    private LocalTime heurePassage;
}
