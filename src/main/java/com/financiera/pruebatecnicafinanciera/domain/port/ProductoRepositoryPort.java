package com.financiera.pruebatecnicafinanciera.domain.port;

import com.financiera.pruebatecnicafinanciera.domain.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {
    Producto guardar(Producto producto);
    Optional<Producto> buscarPorId(Long id);
    List<Producto> buscarTodos();
    List<Producto> buscarPorClienteId(Long clienteId);
    void eliminar(Long id);
    boolean existePorId(Long id);
    boolean existePorNumeroCuenta(String numeroCuenta);
}