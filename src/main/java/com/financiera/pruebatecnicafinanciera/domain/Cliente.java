package com.financiera.pruebatecnicafinanciera.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.regex.Pattern;

public class Cliente {

    // Patrón simple para validar formato xxxx@xxxxx.xxx
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private Long id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String apellido;
    private String correoElectronico;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    public Cliente() {
    }

    public Cliente(Long id, String tipoIdentificacion, String numeroIdentificacion,
                   String nombres, String apellido, String correoElectronico,
                   LocalDate fechaNacimiento) {
        validarNombre(nombres, "nombres");
        validarNombre(apellido, "apellido");
        validarCorreo(correoElectronico);
        validarMayorDeEdad(fechaNacimiento);

        this.id = id;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    // ---------- Validaciones ----------

    private void validarNombre(String valor, String campo) {
        if (valor == null || valor.trim().length() < 2) {
            throw new IllegalArgumentException(
                    "El campo " + campo + " debe tener al menos 2 caracteres");
        }
    }

    private void validarCorreo(String correo) {
        if (correo == null || !EMAIL_PATTERN.matcher(correo).matches()) {
            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido");
        }
    }

    private void validarMayorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null || !esMayorDeEdad(fechaNacimiento)) {
            throw new IllegalArgumentException(
                    "El cliente debe ser mayor de edad para poder registrarse");
        }
    }

    public boolean esMayorDeEdad() {
        return esMayorDeEdad(this.fechaNacimiento);
    }

    private boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    // ---------- Actualización de datos ----------

    public void actualizarDatos(String nombres, String apellido, String correoElectronico) {
        validarNombre(nombres, "nombres");
        validarNombre(apellido, "apellido");
        validarCorreo(correoElectronico);

        this.nombres = nombres;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.fechaModificacion = LocalDateTime.now();
    }

    // ---------- Reconstrucción desde base de datos ----------

    public static Cliente reconstruir(Long id, String tipoIdentificacion, String numeroIdentificacion,
                                      String nombres, String apellido, String correoElectronico,
                                      LocalDate fechaNacimiento, LocalDateTime fechaCreacion,
                                      LocalDateTime fechaModificacion) {
        Cliente cliente = new Cliente();
        cliente.id = id;
        cliente.tipoIdentificacion = tipoIdentificacion;
        cliente.numeroIdentificacion = numeroIdentificacion;
        cliente.nombres = nombres;
        cliente.apellido = apellido;
        cliente.correoElectronico = correoElectronico;
        cliente.fechaNacimiento = fechaNacimiento;
        cliente.fechaCreacion = fechaCreacion;
        cliente.fechaModificacion = fechaModificacion;
        return cliente;
    }

    // ---------- Getters y setters ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
}