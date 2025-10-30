package com.trajets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrajetArretDTO {
    private Long trajetId;
    private String trajetNom;
    private Long arretId;
    private String arretNom;
    private int ordre;
}
