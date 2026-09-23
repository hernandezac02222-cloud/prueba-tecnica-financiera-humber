package com.financiera.pruebatecnicafinanciera.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoJpaRepository extends JpaRepository<ProductoJpaEntity, Long> {
    List<ProductoJpaEntity> findByClienteId(Long clienteId);
    boolean existsByNumeroCuenta(String numeroCuenta);
}