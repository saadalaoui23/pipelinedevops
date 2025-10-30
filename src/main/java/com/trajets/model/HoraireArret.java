package com.trajets.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "horaire_arrets")
@Getter
@Setter
@NoArgsConstructor
public class HoraireArret {

    @EmbeddedId
    private HoraireArretId id = new HoraireArretId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("horaireId")
    @JoinColumn(name = "horaire_id")
    private Horaire horaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("arretId")
    @JoinColumn(name = "arret_id")
    private Arret arret;

    @Column(nullable = false)
    private LocalTime heurePassage;
}
