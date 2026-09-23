package com.financiera.pruebatecnicafinanciera.infrastructure.rest;

import com.financiera.pruebatecnicafinanciera.application.ProductoService;
import com.financiera.pruebatecnicafinanciera.domain.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody ProductoRequest request) {
        Producto creado = productoService.crear(
                request.clienteId(),
                request.tipoCuenta(),
                request.exentaGmf()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Producto buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Producto> listarPorCliente(@PathVariable Long clienteId) {
        return productoService.listarPorCliente(clienteId);
    }

    @PatchMapping("/{id}/activar")
    public Producto activar(@PathVariable Long id) {
        return productoService.activar(id);
    }

    @PatchMapping("/{id}/inactivar")
    public Producto inactivar(@PathVariable Long id) {
        return productoService.inactivar(id);
    }

    @PatchMapping("/{id}/cancelar")
    public Producto cancelar(@PathVariable Long id) {
        return productoService.cancelar(id);
    }

    public record ProductoRequest(
            Long clienteId,
            Producto.TipoCuenta tipoCuenta,
            boolean exentaGmf
    ) {}
}