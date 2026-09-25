package com.financiera.pruebatecnicafinanciera;

import com.financiera.pruebatecnicafinanciera.domain.Cliente;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void deberiaCrearClienteValidoCorrectamente() {
        Cliente cliente = new Cliente(
                1L,
                "CC",
                "123456789",
                "Juan",
                "Perez",
                "juan.perez@correo.com",
                LocalDate.of(1995, 5, 20)
        );

        assertEquals("Juan", cliente.getNombres());
        assertEquals("Perez", cliente.getApellido());
        assertEquals("juan.perez@correo.com", cliente.getCorreoElectronico());
    }

    @Test
    void deberiaLanzarErrorSiNombreEsMuyCorto() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(
                    1L,
                    "CC",
                    "123456789",
                    "J",
                    "Perez",
                    "juan.perez@correo.com",
                    LocalDate.of(1995, 5, 20)
            );
        });
    }

    @Test
    void deberiaLanzarErrorSiCorreoTieneFormatoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(
                    1L,
                    "CC",
                    "123456789",
                    "Juan",
                    "Perez",
                    "correo-invalido",
                    LocalDate.of(1995, 5, 20)
            );
        });
    }

    @Test
    void deberiaLanzarErrorSiClienteEsMenorDeEdad() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(
                    1L,
                    "CC",
                    "123456789",
                    "Juan",
                    "Perez",
                    "juan.perez@correo.com",
                    LocalDate.now().minusYears(10)
            );
        });
    }
}