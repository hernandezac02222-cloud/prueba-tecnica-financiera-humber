package com.financiera.pruebatecnicafinanciera.infrastructure.persistence;

import com.financiera.pruebatecnicafinanciera.domain.Producto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
public class ProductoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false, length = 20)
    private Producto.TipoCuenta tipoCuenta;

    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 10)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Producto.EstadoCuenta estado;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;

    @Column(name = "exenta_gmf", nullable = false)
    private boolean exentaGmf;

    // Relación: muchos productos pertenecen a un cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteJpaEntity cliente;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    public ProductoJpaEntity() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Producto.TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(Producto.TipoCuenta v) { this.tipoCuenta = v; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String v) { this.numeroCuenta = v; }

    public Producto.EstadoCuenta getEstado() { return estado; }
    public void setEstado(Producto.EstadoCuenta v) { this.estado = v; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal v) { this.saldo = v; }

    public boolean isExentaGmf() { return exentaGmf; }
    public void setExentaGmf(boolean v) { this.exentaGmf = v; }

    public ClienteJpaEntity getCliente() { return cliente; }
    public void setCliente(ClienteJpaEntity v) { this.cliente = v; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime v) { this.fechaCreacion = v; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime v) { this.fechaModificacion = v; }
}