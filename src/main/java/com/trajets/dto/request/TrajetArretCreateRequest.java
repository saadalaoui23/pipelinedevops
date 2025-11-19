package com.trajets.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrajetArretCreateRequest {
    
    @NotNull(message = "L'ID du trajet ne peut pas être nul")
    private Long trajetId;
    
    @NotNull(message = "L'ID de l'arrêt ne peut pas être nul")
    private Long arretId;
    
    @Min(value = 1, message = "L'ordre doit être positif")
    private int ordre;
}