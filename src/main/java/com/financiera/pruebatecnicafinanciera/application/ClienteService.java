package com.financiera.pruebatecnicafinanciera.application;

import com.financiera.pruebatecnicafinanciera.domain.Cliente;
import com.financiera.pruebatecnicafinanciera.domain.port.ClienteRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepositoryPort clienteRepositoryPort;

    public ClienteService(ClienteRepositoryPort clienteRepositoryPort) {
        this.clienteRepositoryPort = clienteRepositoryPort;
    }

    public Cliente crear(Cliente cliente) {
        return clienteRepositoryPort.guardar(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepositoryPort.buscarTodos();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id " + id));
    }

    public Cliente actualizar(Long id, String nombres, String apellido, String correoElectronico) {
        Cliente cliente = buscarPorId(id);
        cliente.actualizarDatos(nombres, apellido, correoElectronico);
        return clienteRepositoryPort.guardar(cliente);
    }

    public void eliminar(Long id) {
        buscarPorId(id); // lanza error si no existe
        clienteRepositoryPort.eliminar(id);
    }
}