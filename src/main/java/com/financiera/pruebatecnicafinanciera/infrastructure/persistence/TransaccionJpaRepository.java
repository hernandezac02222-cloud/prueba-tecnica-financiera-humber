package com.financiera.pruebatecnicafinanciera.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionJpaRepository extends JpaRepository<TransaccionJpaEntity, Long> {

    @Query("SELECT t FROM TransaccionJpaEntity t " +
            "WHERE t.cuentaOrigen.id = :cuentaId OR t.cuentaDestino.id = :cuentaId " +
            "ORDER BY t.fecha DESC")
    List<TransaccionJpaEntity> findByCuentaId(@Param("cuentaId") Long cuentaId);
}