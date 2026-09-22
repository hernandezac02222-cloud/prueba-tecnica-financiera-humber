package com.financiera.pruebatecnicafinanciera.infrastructure.rest;

import com.financiera.pruebatecnicafinanciera.application.ClienteService;
import com.financiera.pruebatecnicafinanciera.domain.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody ClienteRequest request) {
        Cliente cliente = new Cliente(
                null,
                request.tipoIdentificacion(),
                request.numeroIdentificacion(),
                request.nombres(),
                request.apellido(),
                request.correoElectronico(),
                request.fechaNacimiento()
        );
        Cliente creado = clienteService.crear(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @RequestBody ClienteUpdateRequest request) {
        return clienteService.actualizar(id, request.nombres(), request.apellido(), request.correoElectronico());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    public record ClienteRequest(
            String tipoIdentificacion,
            String numeroIdentificacion,
            String nombres,
            String apellido,
            String correoElectronico,
            LocalDate fechaNacimiento
    ) {}

    public record ClienteUpdateRequest(
            String nombres,
            String apellido,
            String correoElectronico
    ) {}
}