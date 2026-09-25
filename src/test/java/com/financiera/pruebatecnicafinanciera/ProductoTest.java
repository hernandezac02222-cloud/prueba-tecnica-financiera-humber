package com.financiera.pruebatecnicafinanciera;

import com.financiera.pruebatecnicafinanciera.domain.Producto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void deberiaCrearCuentaDeAhorrosValidaCorrectamente() {
        Producto producto = Producto.crear(1L, Producto.TipoCuenta.AHORROS, "5312345678", false);

        assertEquals(Producto.TipoCuenta.AHORROS, producto.getTipoCuenta());
        assertEquals("5312345678", producto.getNumeroCuenta());
        assertEquals(BigDecimal.ZERO, producto.getSaldo());
        assertEquals(Producto.EstadoCuenta.ACTIVA, producto.getEstado());
    }

    @Test
    void deberiaLanzarErrorSiClienteIdEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            Producto.crear(null, Producto.TipoCuenta.AHORROS, "5312345678", false);
        });
    }

    @Test
    void deberiaLanzarErrorSiNumeroCuentaNoTienePrefijoCorrecto() {
        assertThrows(IllegalArgumentException.class, () -> {
            Producto.crear(1L, Producto.TipoCuenta.AHORROS, "3312345678", false);
        });
    }

    @Test
    void deberiaLanzarErrorSiNumeroCuentaNoTieneDiezDigitos() {
        assertThrows(IllegalArgumentException.class, () -> {
            Producto.crear(1L, Producto.TipoCuenta.AHORROS, "531234", false);
        });
    }
}