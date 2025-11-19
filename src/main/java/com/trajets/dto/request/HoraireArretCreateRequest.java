package com.trajets.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
public class HoraireArretCreateRequest {
    
    @NotNull(message = "L'ID de l'horaire ne peut pas être nul")
    private Long horaireId;
    
    @NotNull(message = "L'ID de l'arrêt ne peut pas être nul")
    private Long arretId;
    
    @NotNull(message = "L'heure de passage ne peut pas être nulle")
    private LocalTime heurePassage;
}