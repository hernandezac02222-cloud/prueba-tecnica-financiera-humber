package com.financiera.pruebatecnicafinanciera.domain.port;

import com.financiera.pruebatecnicafinanciera.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente guardar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    List<Cliente> buscarTodos();
    void eliminar(Long id);
    boolean existePorId(Long id);
}