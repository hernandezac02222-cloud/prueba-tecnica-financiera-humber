package com.financiera.pruebatecnicafinanciera.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Producto {

    public enum TipoCuenta { CORRIENTE, AHORROS }
    public enum EstadoCuenta { ACTIVA, INACTIVA, CANCELADA }

    private static final Pattern NUMERO_CUENTA_PATTERN = Pattern.compile("^\\d{10}$");

    private Long id;
    private TipoCuenta tipoCuenta;
    private String numeroCuenta;
    private EstadoCuenta estado;
    private BigDecimal saldo;
    private boolean exentaGmf;
    private Long clienteId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    private Producto() {
    }

    public static Producto crear(Long clienteId, TipoCuenta tipoCuenta, String numeroCuenta, boolean exentaGmf) {
        validarClienteId(clienteId);
        validarNumeroCuenta(tipoCuenta, numeroCuenta);

        Producto producto = new Producto();
        producto.clienteId = clienteId;
        producto.tipoCuenta = tipoCuenta;
        producto.numeroCuenta = numeroCuenta;
        producto.saldo = BigDecimal.ZERO;
        producto.exentaGmf = exentaGmf;
        producto.estado = EstadoCuenta.ACTIVA;
        producto.fechaCreacion = LocalDateTime.now();
        producto.fechaModificacion = LocalDateTime.now();
        return producto;
    }

    private static void validarClienteId(Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("El producto debe estar vinculado a un cliente existente");
        }
    }

    private static void validarNumeroCuenta(TipoCuenta tipoCuenta, String numeroCuenta) {
        if (numeroCuenta == null || !NUMERO_CUENTA_PATTERN.matcher(numeroCuenta).matches()) {
            throw new IllegalArgumentException("El numero de cuenta debe tener exactamente 10 digitos numericos");
        }
        String prefijoEsperado = tipoCuenta == TipoCuenta.AHORROS ? "53" : "33";
        if (!numeroCuenta.startsWith(prefijoEsperado)) {
            throw new IllegalArgumentException(
                    "El numero de cuenta de tipo " + tipoCuenta + " debe iniciar en " + prefijoEsperado);
        }
    }

    // ---------- Cambios de estado ----------

    public void activar() {
        this.estado = EstadoCuenta.ACTIVA;
        this.fechaModificacion = LocalDateTime.now();
    }

    public void inactivar() {
        this.estado = EstadoCuenta.INACTIVA;
        this.fechaModificacion = LocalDateTime.now();
    }

    public void cancelar() {
        if (saldo.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException("Solo se pueden cancelar cuentas con saldo en $0");
        }
        this.estado = EstadoCuenta.CANCELADA;
        this.fechaModificacion = LocalDateTime.now();
    }

    // ---------- Saldo ----------

    public void actualizarSaldo(BigDecimal nuevoSaldo) {
        if (tipoCuenta == TipoCuenta.AHORROS && nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("La cuenta de ahorros no puede tener un saldo menor a $0");
        }
        this.saldo = nuevoSaldo;
        this.fechaModificacion = LocalDateTime.now();
    }

    // ---------- Reconstrucción desde base de datos ----------

    public static Producto reconstruir(Long id, TipoCuenta tipoCuenta, String numeroCuenta, EstadoCuenta estado,
                                       BigDecimal saldo, boolean exentaGmf, Long clienteId,
                                       LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        Producto producto = new Producto();
        producto.id = id;
        producto.tipoCuenta = tipoCuenta;
        producto.numeroCuenta = numeroCuenta;
        producto.estado = estado;
        producto.saldo = saldo;
        producto.exentaGmf = exentaGmf;
        producto.clienteId = clienteId;
        producto.fechaCreacion = fechaCreacion;
        producto.fechaModificacion = fechaModificacion;
        return producto;
    }

    // ---------- Getters ----------

    public Long getId() {
        return id;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public EstadoCuenta getEstado() {
        return estado;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public boolean isExentaGmf() {
        return exentaGmf;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
}