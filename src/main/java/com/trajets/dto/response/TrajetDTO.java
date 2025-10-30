package com.trajets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrajetDTO {
    private Long id;
    private String nom;
    private Long ligneId;
    private String ligneNom;
}
