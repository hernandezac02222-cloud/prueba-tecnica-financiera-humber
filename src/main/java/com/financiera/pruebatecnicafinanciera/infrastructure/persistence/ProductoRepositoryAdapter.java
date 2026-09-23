package com.financiera.pruebatecnicafinanciera.infrastructure.persistence;

import com.financiera.pruebatecnicafinanciera.domain.Producto;
import com.financiera.pruebatecnicafinanciera.domain.port.ProductoRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    private final ProductoJpaRepository jpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;

    public ProductoRepositoryAdapter(ProductoJpaRepository jpaRepository,
                                     ClienteJpaRepository clienteJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Producto guardar(Producto producto) {
        ProductoJpaEntity entity = toEntity(producto);
        ProductoJpaEntity guardado = jpaRepository.save(entity);
        return toDomain(guardado);
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Producto> buscarTodos() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> buscarPorClienteId(Long clienteId) {
        return jpaRepository.findByClienteId(clienteId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existePorNumeroCuenta(String numeroCuenta) {
        return jpaRepository.existsByNumeroCuenta(numeroCuenta);
    }

    private ProductoJpaEntity toEntity(Producto producto) {
        ProductoJpaEntity entity = new ProductoJpaEntity();
        entity.setId(producto.getId());
        entity.setTipoCuenta(producto.getTipoCuenta());
        entity.setNumeroCuenta(producto.getNumeroCuenta());
        entity.setEstado(producto.getEstado());
        entity.setSaldo(producto.getSaldo());
        entity.setExentaGmf(producto.isExentaGmf());
        entity.setCliente(clienteJpaRepository.getReferenceById(producto.getClienteId()));
        entity.setFechaCreacion(producto.getFechaCreacion());
        entity.setFechaModificacion(producto.getFechaModificacion());
        return entity;
    }

    private Producto toDomain(ProductoJpaEntity entity) {
        return Producto.reconstruir(
                entity.getId(),
                entity.getTipoCuenta(),
                entity.getNumeroCuenta(),
                entity.getEstado(),
                entity.getSaldo(),
                entity.isExentaGmf(),
                entity.getCliente().getId(),
                entity.getFechaCreacion(),
                entity.getFechaModificacion()
        );
    }
}