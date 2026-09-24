package com.financiera.pruebatecnicafinanciera.infrastructure.rest;

import com.financiera.pruebatecnicafinanciera.application.TransaccionService;
import com.financiera.pruebatecnicafinanciera.domain.Transaccion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/consignar")
    public ResponseEntity<Transaccion> consignar(@RequestBody ConsignarRequest request) {
        Transaccion t = transaccionService.consignar(request.cuentaId(), request.monto());
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    @PostMapping("/retirar")
    public ResponseEntity<Transaccion> retirar(@RequestBody RetirarRequest request) {
        Transaccion t = transaccionService.retirar(request.cuentaId(), request.monto());
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    @PostMapping("/transferir")
    public ResponseEntity<Transaccion> transferir(@RequestBody TransferirRequest request) {
        Transaccion t = transaccionService.transferir(
                request.cuentaOrigenId(), request.cuentaDestinoId(), request.monto());
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public List<Transaccion> estadoDeCuenta(@PathVariable Long cuentaId) {
        return transaccionService.estadoDeCuenta(cuentaId);
    }

    public record ConsignarRequest(Long cuentaId, BigDecimal monto) {}
    public record RetirarRequest(Long cuentaId, BigDecimal monto) {}
    public record TransferirRequest(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto) {}
}