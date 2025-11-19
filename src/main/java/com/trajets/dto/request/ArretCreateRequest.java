package com.trajets.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArretCreateRequest {
    
    @NotBlank(message = "Le nom de l'arrêt ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @NotBlank(message = "La localisation ne peut pas être vide")
    @Size(min = 2, max = 255, message = "La localisation doit contenir entre 2 et 255 caractères")
    private String localisation;
}