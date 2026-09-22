package com.financiera.pruebatecnicafinanciera.infrastructure.persistence;

import com.financiera.pruebatecnicafinanciera.domain.Cliente;
import com.financiera.pruebatecnicafinanciera.domain.port.ClienteRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository jpaRepository;

    public ClienteRepositoryAdapter(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        ClienteJpaEntity entity = toEntity(cliente);
        ClienteJpaEntity guardado = jpaRepository.save(entity);
        return toDomain(guardado);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return jpaRepository.findAll().stream()
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

    private ClienteJpaEntity toEntity(Cliente cliente) {
        ClienteJpaEntity entity = new ClienteJpaEntity();
        entity.setId(cliente.getId());
        entity.setTipoIdentificacion(cliente.getTipoIdentificacion());
        entity.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        entity.setNombres(cliente.getNombres());
        entity.setApellido(cliente.getApellido());
        entity.setCorreoElectronico(cliente.getCorreoElectronico());
        entity.setFechaNacimiento(cliente.getFechaNacimiento());
        entity.setFechaCreacion(cliente.getFechaCreacion());
        entity.setFechaModificacion(cliente.getFechaModificacion());
        return entity;
    }

    private Cliente toDomain(ClienteJpaEntity entity) {
        return Cliente.reconstruir(
                entity.getId(),
                entity.getTipoIdentificacion(),
                entity.getNumeroIdentificacion(),
                entity.getNombres(),
                entity.getApellido(),
                entity.getCorreoElectronico(),
                entity.getFechaNacimiento(),
                entity.getFechaCreacion(),
                entity.getFechaModificacion()
        );
    }
}