package com.trajets.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trajet_arrets")
@Getter
@Setter
@NoArgsConstructor
public class TrajetArret {

    @EmbeddedId
    private TrajetArretId id = new TrajetArretId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trajetId")
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("arretId")
    @JoinColumn(name = "arret_id")
    private Arret arret;

    @Column(nullable = false)
    private int ordre;
}
