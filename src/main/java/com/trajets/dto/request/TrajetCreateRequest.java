package com.trajets.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrajetCreateRequest {
    
    @NotBlank(message = "Le nom du trajet ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @NotNull(message = "L'ID de la ligne ne peut pas être nul")
    private Long ligneId;
}