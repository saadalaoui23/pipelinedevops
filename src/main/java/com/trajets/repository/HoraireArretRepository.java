package com.trajets.repository;

import com.trajets.model.HoraireArret;
import com.trajets.model.HoraireArretId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraireArretRepository extends JpaRepository<HoraireArret, HoraireArretId> {
}
