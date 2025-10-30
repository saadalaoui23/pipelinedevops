package com.trajets.repository;

import com.trajets.model.Ligne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LigneRepository extends JpaRepository<Ligne, Long> {
    Optional<Ligne> findByCode(String code);
    boolean existsByCode(String code);
}

