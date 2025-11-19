package com.trajets.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
public class HoraireCreateRequest {
    
    @NotNull(message = "L'heure de départ ne peut pas être nulle")
    private LocalTime heureDepart;
    
    @NotNull(message = "L'heure d'arrivée ne peut pas être nulle")
    private LocalTime heureArrivee;
    
    @NotNull(message = "L'ID du trajet ne peut pas être nul")
    private Long trajetId;
}