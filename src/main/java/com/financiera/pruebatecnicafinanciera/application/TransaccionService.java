package com.financiera.pruebatecnicafinanciera.application;

import com.financiera.pruebatecnicafinanciera.domain.Producto;
import com.financiera.pruebatecnicafinanciera.domain.Transaccion;
import com.financiera.pruebatecnicafinanciera.domain.port.ProductoRepositoryPort;
import com.financiera.pruebatecnicafinanciera.domain.port.TransaccionRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransaccionService {

    private final TransaccionRepositoryPort transaccionRepositoryPort;
    private final ProductoRepositoryPort productoRepositoryPort;

    public TransaccionService(TransaccionRepositoryPort transaccionRepositoryPort,
                              ProductoRepositoryPort productoRepositoryPort) {
        this.transaccionRepositoryPort = transaccionRepositoryPort;
        this.productoRepositoryPort = productoRepositoryPort;
    }

    @Transactional
    public Transaccion consignar(Long cuentaId, BigDecimal monto) {
        Producto cuenta = buscarProducto(cuentaId);
        BigDecimal nuevoSaldo = cuenta.getSaldo().add(monto);
        cuenta.actualizarSaldo(nuevoSaldo);
        productoRepositoryPort.guardar(cuenta);

        Transaccion transaccion = Transaccion.crearConsignacion(cuentaId, monto, nuevoSaldo);
        return transaccionRepositoryPort.guardar(transaccion);
    }

    @Transactional
    public Transaccion retirar(Long cuentaId, BigDecimal monto) {
        Producto cuenta = buscarProducto(cuentaId);
        validarSaldoSuficiente(cuenta, monto);

        BigDecimal nuevoSaldo = cuenta.getSaldo().subtract(monto);
        cuenta.actualizarSaldo(nuevoSaldo);
        productoRepositoryPort.guardar(cuenta);

        Transaccion transaccion = Transaccion.crearRetiro(cuentaId, monto, nuevoSaldo);
        return transaccionRepositoryPort.guardar(transaccion);
    }

    @Transactional
    public Transaccion transferir(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto) {
        Producto cuentaOrigen = buscarProducto(cuentaOrigenId);
        Producto cuentaDestino = buscarProducto(cuentaDestinoId);
        validarSaldoSuficiente(cuentaOrigen, monto);

        BigDecimal nuevoSaldoOrigen = cuentaOrigen.getSaldo().subtract(monto);
        BigDecimal nuevoSaldoDestino = cuentaDestino.getSaldo().add(monto);

        cuentaOrigen.actualizarSaldo(nuevoSaldoOrigen);
        cuentaDestino.actualizarSaldo(nuevoSaldoDestino);
        productoRepositoryPort.guardar(cuentaOrigen);
        productoRepositoryPort.guardar(cuentaDestino);

        Transaccion transaccion = Transaccion.crearTransferencia(
                cuentaOrigenId, cuentaDestinoId, monto, nuevoSaldoOrigen, nuevoSaldoDestino);
        return transaccionRepositoryPort.guardar(transaccion);
    }

    public List<Transaccion> estadoDeCuenta(Long cuentaId) {
        buscarProducto(cuentaId); // valida que la cuenta exista
        return transaccionRepositoryPort.buscarPorCuentaId(cuentaId);
    }

    private Producto buscarProducto(Long cuentaId) {
        return productoRepositoryPort.buscarPorId(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("No existe una cuenta con id " + cuentaId));
    }

    private void validarSaldoSuficiente(Producto cuenta, BigDecimal monto) {
        if (cuenta.getSaldo().compareTo(monto) < 0) {
            throw new IllegalStateException("Saldo insuficiente. Saldo actual: " + cuenta.getSaldo());
        }
    }
}