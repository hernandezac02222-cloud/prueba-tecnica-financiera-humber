package com.financiera.pruebatecnicafinanciera.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {

    public enum TipoTransaccion { CONSIGNACION, RETIRO, TRANSFERENCIA }

    private Long id;
    private TipoTransaccion tipo;
    private BigDecimal monto;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
    private BigDecimal saldoResultanteOrigen;
    private BigDecimal saldoResultanteDestino;
    private LocalDateTime fecha;

    private Transaccion() {
    }

    public static Transaccion crearConsignacion(Long cuentaId, BigDecimal monto, BigDecimal saldoResultante) {
        validarMonto(monto);
        Transaccion t = new Transaccion();
        t.tipo = TipoTransaccion.CONSIGNACION;
        t.cuentaOrigenId = cuentaId;
        t.monto = monto;
        t.saldoResultanteOrigen = saldoResultante;
        t.fecha = LocalDateTime.now();
        return t;
    }

    public static Transaccion crearRetiro(Long cuentaId, BigDecimal monto, BigDecimal saldoResultante) {
        validarMonto(monto);
        Transaccion t = new Transaccion();
        t.tipo = TipoTransaccion.RETIRO;
        t.cuentaOrigenId = cuentaId;
        t.monto = monto;
        t.saldoResultanteOrigen = saldoResultante;
        t.fecha = LocalDateTime.now();
        return t;
    }

    public static Transaccion crearTransferencia(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto,
                                                 BigDecimal saldoResultanteOrigen, BigDecimal saldoResultanteDestino) {
        validarMonto(monto);
        if (cuentaOrigenId.equals(cuentaDestinoId)) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser la misma");
        }
        Transaccion t = new Transaccion();
        t.tipo = TipoTransaccion.TRANSFERENCIA;
        t.cuentaOrigenId = cuentaOrigenId;
        t.cuentaDestinoId = cuentaDestinoId;
        t.monto = monto;
        t.saldoResultanteOrigen = saldoResultanteOrigen;
        t.saldoResultanteDestino = saldoResultanteDestino;
        t.fecha = LocalDateTime.now();
        return t;
    }

    private static void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transaccion debe ser mayor a $0");
        }
    }

    public static Transaccion reconstruir(Long id, TipoTransaccion tipo, BigDecimal monto, Long cuentaOrigenId,
                                          Long cuentaDestinoId, BigDecimal saldoResultanteOrigen,
                                          BigDecimal saldoResultanteDestino, LocalDateTime fecha) {
        Transaccion t = new Transaccion();
        t.id = id;
        t.tipo = tipo;
        t.monto = monto;
        t.cuentaOrigenId = cuentaOrigenId;
        t.cuentaDestinoId = cuentaDestinoId;
        t.saldoResultanteOrigen = saldoResultanteOrigen;
        t.saldoResultanteDestino = saldoResultanteDestino;
        t.fecha = fecha;
        return t;
    }

    public Long getId() { return id; }
    public TipoTransaccion getTipo() { return tipo; }
    public BigDecimal getMonto() { return monto; }
    public Long getCuentaOrigenId() { return cuentaOrigenId; }
    public Long getCuentaDestinoId() { return cuentaDestinoId; }
    public BigDecimal getSaldoResultanteOrigen() { return saldoResultanteOrigen; }
    public BigDecimal getSaldoResultanteDestino() { return saldoResultanteDestino; }
    public LocalDateTime getFecha() { return fecha; }
}