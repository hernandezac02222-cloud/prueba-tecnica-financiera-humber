package com.financiera.pruebatecnicafinanciera.infrastructure.persistence;

import com.financiera.pruebatecnicafinanciera.domain.Transaccion;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
public class TransaccionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Transaccion.TipoTransaccion tipo;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_origen_id", nullable = false)
    private ProductoJpaEntity cuentaOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_destino_id")
    private ProductoJpaEntity cuentaDestino;

    @Column(name = "saldo_resultante_origen", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoResultanteOrigen;

    @Column(name = "saldo_resultante_destino", precision = 19, scale = 2)
    private BigDecimal saldoResultanteDestino;

    @Column(nullable = false)
    private LocalDateTime fecha;

    public TransaccionJpaEntity() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Transaccion.TipoTransaccion getTipo() { return tipo; }
    public void setTipo(Transaccion.TipoTransaccion v) { this.tipo = v; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal v) { this.monto = v; }

    public ProductoJpaEntity getCuentaOrigen() { return cuentaOrigen; }
    public void setCuentaOrigen(ProductoJpaEntity v) { this.cuentaOrigen = v; }

    public ProductoJpaEntity getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(ProductoJpaEntity v) { this.cuentaDestino = v; }

    public BigDecimal getSaldoResultanteOrigen() { return saldoResultanteOrigen; }
    public void setSaldoResultanteOrigen(BigDecimal v) { this.saldoResultanteOrigen = v; }

    public BigDecimal getSaldoResultanteDestino() { return saldoResultanteDestino; }
    public void setSaldoResultanteDestino(BigDecimal v) { this.saldoResultanteDestino = v; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime v) { this.fecha = v; }
}