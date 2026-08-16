# Modelo de Datos v0.1 — EventHub

## 1. Entidades

### Usuario
Representa a las personas que utilizan la plataforma.

**Atributos:**
- usuario_id (PK)
- nombre
- apellido
- correo
- telefono
- contrasena
- rol
- estado

### Evento
Representa una fiesta, concierto, festival o evento de entretenimiento.

**Atributos:**
- evento_id (PK)
- nombre
- descripcion
- fecha
- hora_inicio
- hora_fin
- edad_minima
- capacidad
- estado

### Local
Representa el lugar físico donde se realiza un evento.

**Atributos:**
- local_id (PK)
- nombre
- direccion
- telefono
- capacidad
- estado

### Entrada
Representa el acceso adquirido para asistir a un evento.

**Atributos:**
- entrada_id (PK)
- codigo
- tipo
- precio
- fecha_compra
- qr
- estado

### Mesa
Representa un espacio disponible para reserva dentro de un local.

**Atributos:**
- mesa_id (PK)
- numero
- capacidad
- ubicacion
- precio_reserva
- estado

### ReservaMesa
Representa la reserva de una mesa realizada para un evento.

**Atributos:**
- reserva_id (PK)
- fecha_reserva
- cantidad_personas
- monto
- estado

### Producto
Representa una bebida, comida u otro producto ofrecido durante un evento.

**Atributos:**
- producto_id (PK)
- nombre
- tipo
- precio
- stock
- estado

### Pedido
Representa una solicitud de productos realizada durante un evento.

**Atributos:**
- pedido_id (PK)
- fecha
- total
- estado

### DetallePedido
Representa cada producto incluido dentro de un pedido.

**Atributos:**
- detalle_id (PK)
- cantidad
- precio_unitario
- subtotal

### Revendedor
Representa a una persona autorizada para comercializar entradas.

**Atributos:**
- revendedor_id (PK)
- codigo_revendedor
- comision
- estado

### VentaEntrada
Representa una venta de entrada realizada por un revendedor.

**Atributos:**
- venta_id (PK)
- fecha_venta
- precio_venta
- comision


## 2. Relaciones y cardinalidades

| Entidad A | Relación | Entidad B | Cardinalidad |
|---|---|---|---|
| Usuario | compra | Entrada | Usuario 0..N — Entrada 1..1 |
| Evento | ofrece | Entrada | Evento 1..N — Entrada 1..1 |
| Local | alberga | Evento | Local 0..N — Evento 1..1 |
| Local | tiene | Mesa | Local 1..N — Mesa 1..1 |
| Usuario | realiza | ReservaMesa | Usuario 0..N — ReservaMesa 1..1 |
| Evento | tiene | ReservaMesa | Evento 0..N — ReservaMesa 1..1 |
| Mesa | es reservada mediante | ReservaMesa | Mesa 0..N — ReservaMesa 1..1 |
| Usuario | realiza | Pedido | Usuario 0..N — Pedido 1..1 |
| Evento | genera | Pedido | Evento 0..N — Pedido 1..1 |
| Mesa | recibe | Pedido | Mesa 0..N — Pedido 0..1 |
| Pedido | contiene | DetallePedido | Pedido 1..N — DetallePedido 1..1 |
| Producto | aparece en | DetallePedido | Producto 0..N — DetallePedido 1..1 |
| Usuario | puede ser | Revendedor | Usuario 0..1 — Revendedor 1..1 |
| Revendedor | realiza | VentaEntrada | Revendedor 0..N — VentaEntrada 1..1 |
| Entrada | corresponde a | VentaEntrada | Entrada 0..1 — VentaEntrada 1..1 |


## 3. Relaciones N:M resueltas

### Pedido y Producto

Un pedido puede contener varios productos y un producto puede aparecer en muchos pedidos.

La relación original es:

**Pedido N:M Producto**

Se resuelve mediante la entidad asociativa:

**DetallePedido**

Queda:

**Pedido 1:N DetallePedido**

**Producto 1:N DetallePedido**

DetallePedido conserva los atributos propios de la relación:

- cantidad
- precio_unitario
- subtotal


## 4. Claves

Cada entidad posee una clave primaria (PK) que identifica de forma única cada registro.

Las relaciones 1:N se implementarán posteriormente mediante claves foráneas (FK) en el lado N.

Las FK definitivas y sus restricciones serán detalladas en el modelo lógico.

## 5. Observación

Este documento representa la versión conceptual inicial del modelo de datos de EventHub.

Las entidades, atributos y cardinalidades podrán refinarse al descubrir nuevas reglas de negocio.

Todavía no se define el DDL de PostgreSQL.