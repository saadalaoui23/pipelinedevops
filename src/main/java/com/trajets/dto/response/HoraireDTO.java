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
public class HoraireDTO {
    private Long id;
    private LocalTime heureDepart;
    private LocalTime heureArrivee;
    private Long trajetId;
    private String trajetNom;
}
