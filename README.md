# Prueba Técnica - Sistema Financiero

API REST para la gestión de clientes, productos financieros (cuentas de ahorro y corriente) y transacciones (consignaciones, retiros y transferencias) de una entidad financiera.

## Tecnologías utilizadas

- Java 17
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL 16
- Docker y Docker Compose
- Arquitectura Hexagonal (Puertos y Adaptadores)
- Maven

## Arquitectura

El proyecto sigue una arquitectura hexagonal, separada en tres capas:

- **domain**: entidades de negocio y reglas de validación (Cliente, Producto, Transaccion), además de los puertos (interfaces) que definen los contratos de persistencia.
- **application**: servicios que orquestan la lógica de negocio (ClienteService, ProductoService, TransaccionService).
- **infrastructure**: adaptadores que implementan los puertos, incluyendo persistencia con JPA (infrastructure/persistence) y los controladores REST (infrastructure/rest).

## Cómo levantar el proyecto con Docker

Requisitos: tener Docker Desktop instalado y corriendo.

1. Clonar el repositorio.
2. Ubicarse en la carpeta raíz del proyecto (donde está el archivo `docker-compose.yml`).
3. Ejecutar el siguiente comando:
4. Esto va a levantar dos contenedores:
    - `postgres-financiera`: la base de datos PostgreSQL.
    - `app-financiera`: la aplicación Spring Boot, expuesta en el puerto 8080.

5. Una vez levantado, la API estará disponible en `http://localhost:8080`.

## Endpoints principales

### Clientes
- `POST /api/clientes` - Crear cliente
- `GET /api/clientes` - Listar todos los clientes
- `GET /api/clientes/{id}` - Buscar cliente por id
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente

### Productos
- `POST /api/productos` - Crear producto (cuenta)
- `GET /api/productos` - Listar todos los productos
- `GET /api/productos/{id}` - Buscar producto por id
- `GET /api/productos/cliente/{clienteId}` - Buscar productos por cliente
- `PATCH /api/productos/{id}/activar` - Activar cuenta
- `PATCH /api/productos/{id}/inactivar` - Inactivar cuenta
- `PATCH /api/productos/{id}/cancelar` - Cancelar cuenta

### Transacciones
- `POST /api/transacciones/consignar` - Consignar dinero a una cuenta
- `POST /api/transacciones/retirar` - Retirar dinero de una cuenta
- `POST /api/transacciones/transferir` - Transferir dinero entre cuentas
- `GET /api/transacciones/cuenta/{cuentaId}` - Ver historial de movimientos de una cuenta

## Autor

Álvaro Humberty Hernández Córdoba
