package com.financiera.pruebatecnicafinanciera.application;

import com.financiera.pruebatecnicafinanciera.domain.Producto;
import com.financiera.pruebatecnicafinanciera.domain.port.ClienteRepositoryPort;
import com.financiera.pruebatecnicafinanciera.domain.port.ProductoRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

@Service
public class ProductoService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final ProductoRepositoryPort productoRepositoryPort;
    private final ClienteRepositoryPort clienteRepositoryPort;

    public ProductoService(ProductoRepositoryPort productoRepositoryPort,
                           ClienteRepositoryPort clienteRepositoryPort) {
        this.productoRepositoryPort = productoRepositoryPort;
        this.clienteRepositoryPort = clienteRepositoryPort;
    }

    public Producto crear(Long clienteId, Producto.TipoCuenta tipoCuenta, boolean exentaGmf) {
        if (!clienteRepositoryPort.existePorId(clienteId)) {
            throw new IllegalArgumentException("No existe un cliente con id " + clienteId);
        }

        String numeroCuenta = generarNumeroCuentaUnico(tipoCuenta);
        Producto producto = Producto.crear(clienteId, tipoCuenta, numeroCuenta, exentaGmf);
        return productoRepositoryPort.guardar(producto);
    }

    public List<Producto> listarTodos() {
        return productoRepositoryPort.buscarTodos();
    }

    public List<Producto> listarPorCliente(Long clienteId) {
        return productoRepositoryPort.buscarPorClienteId(clienteId);
    }

    public Producto buscarPorId(Long id) {
        return productoRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id " + id));
    }

    public Producto activar(Long id) {
        Producto producto = buscarPorId(id);
        producto.activar();
        return productoRepositoryPort.guardar(producto);
    }

    public Producto inactivar(Long id) {
        Producto producto = buscarPorId(id);
        producto.inactivar();
        return productoRepositoryPort.guardar(producto);
    }

    public Producto cancelar(Long id) {
        Producto producto = buscarPorId(id);
        producto.cancelar();
        return productoRepositoryPort.guardar(producto);
    }

    public Producto actualizarSaldo(Long id, BigDecimal nuevoSaldo) {
        Producto producto = buscarPorId(id);
        producto.actualizarSaldo(nuevoSaldo);
        return productoRepositoryPort.guardar(producto);
    }

    // ---------- Generación del número de cuenta ----------

    private String generarNumeroCuentaUnico(Producto.TipoCuenta tipoCuenta) {
        String prefijo = tipoCuenta == Producto.TipoCuenta.AHORROS ? "53" : "33";
        String numeroCuenta;
        do {
            numeroCuenta = prefijo + generarOchoDigitosAleatorios();
        } while (productoRepositoryPort.existePorNumeroCuenta(numeroCuenta));
        return numeroCuenta;
    }

    private String generarOchoDigitosAleatorios() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}