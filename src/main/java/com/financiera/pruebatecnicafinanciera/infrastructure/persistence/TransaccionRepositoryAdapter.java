package com.financiera.pruebatecnicafinanciera.infrastructure.persistence;

import com.financiera.pruebatecnicafinanciera.domain.Transaccion;
import com.financiera.pruebatecnicafinanciera.domain.port.TransaccionRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransaccionRepositoryAdapter implements TransaccionRepositoryPort {

    private final TransaccionJpaRepository jpaRepository;
    private final ProductoJpaRepository productoJpaRepository;

    public TransaccionRepositoryAdapter(TransaccionJpaRepository jpaRepository,
                                        ProductoJpaRepository productoJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.productoJpaRepository = productoJpaRepository;
    }

    @Override
    public Transaccion guardar(Transaccion transaccion) {
        TransaccionJpaEntity entity = toEntity(transaccion);
        TransaccionJpaEntity guardada = jpaRepository.save(entity);
        return toDomain(guardada);
    }

    @Override
    public List<Transaccion> buscarPorCuentaId(Long cuentaId) {
        return jpaRepository.findByCuentaId(cuentaId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private TransaccionJpaEntity toEntity(Transaccion transaccion) {
        TransaccionJpaEntity entity = new TransaccionJpaEntity();
        entity.setId(transaccion.getId());
        entity.setTipo(transaccion.getTipo());
        entity.setMonto(transaccion.getMonto());
        entity.setCuentaOrigen(productoJpaRepository.getReferenceById(transaccion.getCuentaOrigenId()));
        if (transaccion.getCuentaDestinoId() != null) {
            entity.setCuentaDestino(productoJpaRepository.getReferenceById(transaccion.getCuentaDestinoId()));
        }
        entity.setSaldoResultanteOrigen(transaccion.getSaldoResultanteOrigen());
        entity.setSaldoResultanteDestino(transaccion.getSaldoResultanteDestino());
        entity.setFecha(transaccion.getFecha());
        return entity;
    }

    private Transaccion toDomain(TransaccionJpaEntity entity) {
        return Transaccion.reconstruir(
                entity.getId(),
                entity.getTipo(),
                entity.getMonto(),
                entity.getCuentaOrigen().getId(),
                entity.getCuentaDestino() != null ? entity.getCuentaDestino().getId() : null,
                entity.getSaldoResultanteOrigen(),
                entity.getSaldoResultanteDestino(),
                entity.getFecha()
        );
    }
}