package com.financiera.pruebatecnicafinanciera.domain.port;

import com.financiera.pruebatecnicafinanciera.domain.Transaccion;

import java.util.List;

public interface TransaccionRepositoryPort {
    Transaccion guardar(Transaccion transaccion);
    List<Transaccion> buscarPorCuentaId(Long cuentaId);
}