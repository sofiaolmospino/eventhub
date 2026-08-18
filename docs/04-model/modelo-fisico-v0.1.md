# Modelo físico v0.1 — EventHub

## 1. Fuente

Proyecto oficial:

- PA-10 EventHub — Gestión de Eventos, Inscripciones y Control de Asistencia.

Fuentes utilizadas:

- Ficha oficial PA-10.
- model-conceptual-v0.1.md
- model-relational-v0.1.md
- der-logico-v0.1.md
- diccionario-datos-v0.1.md
- decisiones-integridad-v0.1.md

Flujo crítico:

Organizador publica evento -> participante se inscribe -> sistema controla
cupo/lista de espera -> se registra asistencia -> se habilita certificado
según criterio.

---

## 2. Estrategia de identificadores

Se propone utilizar `BIGINT` autogenerado para las PK técnicas.

Las PK técnicas identifican filas y no sustituyen las claves naturales o
unicidades de negocio.

---

## 3. USUARIO

Propósito:

Representa la identidad que realiza operaciones dentro del sistema y permite
registrar quién ejecutó cambios relevantes.

| Columna    | Tipo candidato | NULL | Rol/Restricción | Fuente |
|------------|----------------|------|-----------------|--------|
| usuario_id | BIGINT         | NO   | PK              | Diseño |
| nombre     | VARCHAR(100)   | NO   | NN              | RF-04  |
| apellido   | VARCHAR(100)   | NO   | NN              | RF-04  |
| correo     | VARCHAR(254)   | NO   | NN              | RF-04  |
| rol         | VARCHAR(50)    | NO   | NN              | RF-04  |
| created_at | TIMESTAMPTZ    | NO   | Auditoría       | RF-04  |
| updated_at | TIMESTAMPTZ    | NO   | Auditoría       | RF-04  |

### Decisiones

`usuario_id` es la PK técnica.

El atributo `rol` permite diferenciar los tipos de usuario necesarios para
la operación del sistema.

El correo de usuario no se establece como UNIQUE en esta versión porque el
grupo decidió que un mismo correo puede pertenecer a más de un participante.
La política definitiva de identidad/autenticación podrá refinarse
posteriormente.

---

## 4. EVENTO

Propósito:

Representa un evento que puede ser publicado y utilizado para gestionar
inscripciones, asistencia y certificado.

| Columna      | Tipo candidato | NULL | Rol/Restricción     | Fuente       |
|--------------|----------------|------|---------------------|--------------|
| evento_id    | BIGINT         | NO   | PK                  | Diseño       |
| nombre       | VARCHAR(150)   | NO   | NN                  | RN-01, RF-05 |
| descripcion  | TEXT           | SÍ   | —                   | RF-05        |
| responsable  | VARCHAR(150)   | NO   | NN                  | RN-01        |
| modalidad    | VARCHAR(50)    | NO   | NN                  | RN-01        |
| fecha_inicio | TIMESTAMPTZ    | NO   | NN                  | RN-01        |
| fecha_fin    | TIMESTAMPTZ    | NO   | NN                  | RN-01        |
| capacidad    | INTEGER        | NO   | NN, CHECK > 0       | RN-01, RN-03 |
| estado       | VARCHAR(20)    | NO   | NN, CHECK/dominio   | RN-02        |
| usuario_id   | BIGINT         | NO   | FK -> usuario.usuario_id | RF-04 |
| created_at   | TIMESTAMPTZ    | NO   | Auditoría           | RF-04        |
| updated_at   | TIMESTAMPTZ    | NO   | Auditoría           | RF-04        |

### Estados válidos

- BORRADOR
- PUBLICADO
- EN_CURSO
- FINALIZADO
- CANCELADO

### Decisiones

`descripcion` puede ser NULL porque un evento puede ser válido aunque todavía
no tenga una descripción extensa.

`capacidad` es obligatoria y debe ser positiva.

`estado` utiliza un conjunto cerrado de valores.

`usuario_id` identifica al usuario responsable del último cambio relevante
registrado sobre el evento.

### Regla que no se resuelve sólo con constraint simple

RN-03 requiere controlar el número de inscripciones frente a la capacidad
del evento.

Esto requiere lógica entre múltiples filas.

---

## 5. SESION_EVENTO

Propósito:

Representa una sesión concreta perteneciente a un evento.

| Columna          | Tipo candidato | NULL | Rol/Restricción             | Fuente |
|------------------|----------------|------|-----------------------------|--------|
| sesion_evento_id | BIGINT         | NO   | PK                          | Diseño |
| evento_id        | BIGINT         | NO   | FK -> evento.evento_id      | RF-06  |
| fecha_inicio     | TIMESTAMPTZ    | NO   | NN                          | RF-06  |
| fecha_fin        | TIMESTAMPTZ    | NO   | NN, CHECK fin > inicio      | RF-06  |
| created_at       | TIMESTAMPTZ    | NO   | Auditoría candidata         | RF-04  |
| updated_at       | TIMESTAMPTZ    | NO   | Auditoría candidata         | RF-04  |

### Decisiones

Se utiliza TIMESTAMPTZ porque interesa el instante concreto de inicio y
finalización de la sesión.

`evento_id` es obligatorio porque una sesión no tiene sentido sin un evento.

La asistencia se relaciona con esta tabla mediante
`asistencia.sesion_evento_id`.

---

## 6. SEDE

Propósito:

Representa una sede donde pueden desarrollarse eventos.

| Columna    | Tipo candidato | NULL | Rol/Restricción     | Fuente |
|------------|----------------|------|---------------------|--------|
| sede_id    | BIGINT         | NO   | PK                  | Diseño |
| nombre     | VARCHAR(120)   | NO   | NN                  | RF-07  |
| direccion  | VARCHAR(250)   | NO   | NN                  | RF-07  |
| created_at | TIMESTAMPTZ    | NO   | Auditoría candidata | RF-04  |
| updated_at | TIMESTAMPTZ    | NO   | Auditoría candidata | RF-04  |

### Decisiones

La dirección se almacena como texto porque el alcance actual no exige
geolocalización ni descomposición de dirección.

---

## 7. SALA

Propósito:

Representa un espacio físico perteneciente a una sede.

| Columna    | Tipo candidato | NULL | Rol/Restricción     | Fuente |
|------------|----------------|------|---------------------|--------|
| sala_id    | BIGINT         | NO   | PK                  | Diseño |
| sede_id    | BIGINT         | NO   | FK -> sede.sede_id  | RF-07  |
| nombre     | VARCHAR(100)   | NO   | NN                  | RF-07  |
| capacidad  | INTEGER        | NO   | NN, CHECK > 0       | RF-07  |
| created_at | TIMESTAMPTZ    | NO   | Auditoría candidata | RF-04  |
| updated_at | TIMESTAMPTZ    | NO   | Auditoría candidata | RF-04  |

### Decisiones

`capacidad` debe ser positiva.

`sede_id` es obligatorio porque una sala pertenece a una sede.

---

## 8. PARTICIPANTE

Propósito:

Representa al usuario externo o beneficiario que consulta eventos y realiza
acciones de participación.

| Columna         | Tipo candidato | NULL | Rol/Restricción | Fuente       |
|-----------------|----------------|------|-----------------|--------------|
| participante_id | BIGINT         | NO   | PK              | Diseño       |
| nombre          | VARCHAR(100)   | NO   | NN              | RF-08        |
| apellido        | VARCHAR(100)   | NO   | NN              | RF-08        |
| correo          | VARCHAR(254)   | NO   | NN              | RF-01, RF-08 |
| created_at      | TIMESTAMPTZ    | NO   | Auditoría       | RF-04        |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría       | RF-04        |

### Decisiones

`correo` NO es UNIQUE.

El modelo permite que un mismo correo pertenezca a más de un participante.

No se utiliza como PK porque es un dato de negocio.

---

## 9. INSCRIPCION

Propósito:

Representa la inscripción de un participante a un evento.

| Columna           | Tipo candidato | NULL | Rol/Restricción                    | Fuente |
|-------------------|----------------|------|------------------------------------|--------|
| inscripcion_id    | BIGINT         | NO   | PK                                 | Diseño |
| participante_id   | BIGINT         | NO   | FK -> participante.participante_id | RF-08  |
| evento_id         | BIGINT         | NO   | FK -> evento.evento_id             | RF-08  |
| fecha_inscripcion | TIMESTAMPTZ    | NO   | NN                                 | RF-08  |
| estado            | VARCHAR(30)    | NO   | NN, dominio controlado             | RF-08  |
| usuario_id        | BIGINT         | NO   | FK -> usuario.usuario_id           | RF-04  |
| created_at        | TIMESTAMPTZ    | NO   | Auditoría                          | RF-04  |
| updated_at        | TIMESTAMPTZ    | NO   | Auditoría                          | RF-04  |

### Restricción

`UNIQUE(participante_id, evento_id)`

### Estados válidos

- PENDIENTE
- CONFIRMADA
- CANCELADA

### Decisión

Un participante puede inscribirse en muchos eventos, pero no puede tener dos
inscripciones para el mismo evento.

`usuario_id` identifica al usuario que realizó el último cambio relevante de
estado.

### Regla que no se resuelve sólo con constraint simple

RN-03:

El sistema debe respetar la capacidad del evento y utilizar lista de espera
cuando corresponda.

Esto requiere comparar múltiples inscripciones con la capacidad del evento.

---

## 10. LISTA_ESPERA

Propósito:

Representa a un participante que espera disponibilidad para un evento.

| Columna         | Tipo candidato | NULL | Rol/Restricción                    | Fuente |
|-----------------|----------------|------|------------------------------------|--------|
| lista_espera_id | BIGINT         | NO   | PK                                 | Diseño |
| participante_id | BIGINT         | NO   | FK -> participante.participante_id | RF-09  |
| evento_id       | BIGINT         | NO   | FK -> evento.evento_id             | RF-09  |
| fecha_ingreso   | TIMESTAMPTZ    | NO   | NN                                 | RN-05  |
| posicion        | INTEGER        | NO   | NN, CHECK > 0                      | RN-05  |
| estado          | VARCHAR(30)    | NO   | NN, dominio controlado             | RN-05  |
| usuario_id      | BIGINT         | NO   | FK -> usuario.usuario_id           | RF-04  |
| created_at      | TIMESTAMPTZ    | NO   | Auditoría                          | RF-04  |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría                          | RF-04  |

### Restricción

`UNIQUE(participante_id, evento_id)`

### Estados válidos

- ACTIVA
- PROMOVIDA
- CANCELADA

### Decisiones

`posicion` es un entero positivo porque representa el orden dentro de la
lista.

No se permite que un participante vuelva a ingresar a la lista de espera
del mismo evento.

`usuario_id` identifica al usuario que realizó el último cambio relevante
de estado.

### Regla que no se resuelve sólo con constraint simple

RN-05:

La promoción debe ser ordenada y trazable.

La selección del siguiente participante requiere lógica transaccional.

---

## 11. ASISTENCIA

Propósito:

Representa el registro de asistencia de un participante a una sesión
específica de un evento.

| Columna          | Tipo candidato | NULL | Rol/Restricción                       | Fuente       |
|------------------|----------------|------|---------------------------------------|--------------|
| asistencia_id    | BIGINT         | NO   | PK                                    | Diseño       |
| participante_id  | BIGINT         | NO   | FK -> participante.participante_id    | RF-10        |
| sesion_evento_id | BIGINT         | NO   | FK -> sesion_evento.sesion_evento_id  | RN-06, RF-10 |
| fecha_registro   | TIMESTAMPTZ    | NO   | NN                                    | RF-18        |
| estado           | VARCHAR(30)    | NO   | NN, dominio controlado                | RF-10        |
| usuario_id       | BIGINT         | NO   | FK -> usuario.usuario_id              | RF-04        |
| created_at       | TIMESTAMPTZ    | NO   | Auditoría                             | RF-04        |
| updated_at       | TIMESTAMPTZ    | NO   | Auditoría                             | RF-04        |

### Restricción

`UNIQUE(participante_id, sesion_evento_id)`

Esta restricción evita registrar dos veces la asistencia del mismo participante
para una misma sesión.

### Estados válidos

- PRESENTE
- AUSENTE

### Decisión

La asistencia se registra por `SESION_EVENTO`.

Por lo tanto, `sesion_evento_id` es obligatorio y funciona como FK hacia
`sesion_evento.sesion_evento_id`.

La asistencia registrada por sesión permite calcular posteriormente el
porcentaje de asistencia utilizado para habilitar certificados.

`usuario_id` identifica al usuario que registró o modificó la asistencia.

---

## 12. CERTIFICADO

Propósito:

Representa el certificado académico simple habilitado para un participante
que cumple el criterio de asistencia.

| Columna               | Tipo candidato | NULL | Rol/Restricción                    | Fuente |
|-----------------------|----------------|------|------------------------------------|--------|
| certificado_id        | BIGINT         | NO   | PK                                 | Diseño |
| participante_id       | BIGINT         | NO   | FK -> participante.participante_id | RF-12  |
| evento_id             | BIGINT         | NO   | FK -> evento.evento_id             | RF-12  |
| fecha_emision         | TIMESTAMPTZ    | NO   | NN                                 | RF-12  |
| porcentaje_asistencia | NUMERIC(5,2)   | NO   | CHECK 0..100                       | RN-07  |
| estado                | VARCHAR(30)    | NO   | NN, dominio controlado             | RF-12  |
| usuario_id            | BIGINT         | NO   | FK -> usuario.usuario_id           | RF-04  |
| created_at             | TIMESTAMPTZ    | NO   | Auditoría                          | RF-04  |
| updated_at             | TIMESTAMPTZ    | NO   | Auditoría                          | RF-04  |

### Restricción

`UNIQUE(participante_id, evento_id)`

### Estados válidos

- HABILITADO
- EMITIDO
- ANULADO

### Decisión

Sólo puede existir un certificado por participante y evento.

El certificado sólo se habilita cuando se cumple el porcentaje mínimo de
asistencia.

`usuario_id` identifica al usuario que realizó el último cambio relevante
del certificado.

### Regla que no se resuelve sólo con constraint simple

RN-07:

El certificado sólo se habilita cuando se cumple el porcentaje mínimo de
asistencia.

Esto depende de información relacionada con las asistencias registradas.

---

## 13. COMUNICACION

Propósito:

Representa una comunicación relacionada con un evento.

| Columna         | Tipo candidato | NULL | Rol/Restricción        | Fuente |
|-----------------|----------------|------|------------------------|--------|
| comunicacion_id | BIGINT         | NO   | PK                     | Diseño |
| evento_id       | BIGINT         | NO   | FK -> evento.evento_id | RF-11  |
| asunto          | VARCHAR(200)   | NO   | NN                     | RF-11  |
| contenido       | TEXT           | NO   | NN                     | RF-11  |
| fecha_envio     | TIMESTAMPTZ    | NO   | NN                     | RF-11  |
| estado          | VARCHAR(30)    | NO   | NN, dominio controlado | RF-11  |
| created_at      | TIMESTAMPTZ    | NO   | Auditoría candidata   | RF-04  |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría candidata   | RF-04  |

### Decisión pendiente

Debe definirse si la comunicación se dirige a todos los participantes de un
evento o si necesita destinatarios individuales.

Esta decisión no bloquea el núcleo de Clase 05.

---

## 14. FEEDBACK

Propósito:

Representa la valoración o comentario de un participante sobre un evento.

| Columna         | Tipo candidato | NULL | Rol/Restricción                    | Fuente |
|-----------------|----------------|------|------------------------------------|--------|
| feedback_id     | BIGINT         | NO   | PK                                 | Diseño |
| participante_id | BIGINT         | NO   | FK -> participante.participante_id | RF-13  |
| evento_id       | BIGINT         | NO   | FK -> evento.evento_id             | RF-13  |
| valoracion      | INTEGER        | NO   | CHECK según escala definida        | RF-13  |
| comentario      | TEXT           | SÍ   | —                                  | RF-13  |
| fecha           | TIMESTAMPTZ    | NO   | NN                                 | RF-13  |
| created_at      | TIMESTAMPTZ    | NO   | Auditoría candidata               | RF-04  |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría candidata               | RF-04  |

### Restricción

`UNIQUE(participante_id, evento_id)`

Un participante puede registrar como máximo un feedback para un evento.

La escala exacta de valoración queda pendiente de definición.

---

## 15. Relaciones FK

Las FK principales son:

- `evento.usuario_id -> usuario.usuario_id`
- `sesion_evento.evento_id -> evento.evento_id`
- `sala.sede_id -> sede.sede_id`
- `inscripcion.participante_id -> participante.participante_id`
- `inscripcion.evento_id -> evento.evento_id`
- `inscripcion.usuario_id -> usuario.usuario_id`
- `lista_espera.participante_id -> participante.participante_id`
- `lista_espera.evento_id -> evento.evento_id`
- `lista_espera.usuario_id -> usuario.usuario_id`
- `asistencia.participante_id -> participante.participante_id`
- `asistencia.sesion_evento_id -> sesion_evento.sesion_evento_id`
- `asistencia.usuario_id -> usuario.usuario_id`
- `certificado.participante_id -> participante.participante_id`
- `certificado.evento_id -> evento.evento_id`
- `certificado.usuario_id -> usuario.usuario_id`
- `comunicacion.evento_id -> evento.evento_id`
- `feedback.participante_id -> participante.participante_id`
- `feedback.evento_id -> evento.evento_id`

---

## 16. Reglas transaccionales identificadas

Las siguientes reglas no se reducen a un CHECK simple:

### RN-03 — Control de cupo

Debe comparar inscripciones válidas con la capacidad del evento.

### RN-05 — Promoción de lista de espera

Debe seleccionar participantes de manera ordenada y trazable.

### RN-06 — Registro de asistencia

La asistencia se registra por sesión de evento.

La relación se implementa mediante `sesion_evento_id`, que referencia a
`sesion_evento.sesion_evento_id`.

La asistencia registrada por sesión también sirve como base para calcular el
porcentaje de asistencia utilizado posteriormente en el certificado.

### RN-07 — Certificado

Debe verificar el porcentaje mínimo de asistencia antes de habilitarlo.

### RF-04 — Auditoría

Las operaciones de cambio de estado requieren conservar fecha y usuario.

La fecha se conserva mediante los campos de auditoría y el usuario mediante
`usuario_id`.

---

## 17. Restricciones de dominio

### EVENTO.estado

Valores permitidos:

- BORRADOR
- PUBLICADO
- EN_CURSO
- FINALIZADO
- CANCELADO

### INSCRIPCION.estado

Valores permitidos:

- PENDIENTE
- CONFIRMADA
- CANCELADA

### LISTA_ESPERA.estado

Valores permitidos:

- ACTIVA
- PROMOVIDA
- CANCELADA

### ASISTENCIA.estado

Valores permitidos:

- PRESENTE
- AUSENTE

### CERTIFICADO.estado

Valores permitidos:

- HABILITADO
- EMITIDO
- ANULADO

### EVENTO.capacidad

Debe ser mayor que cero.

### SALA.capacidad

Debe ser mayor que cero.

### LISTA_ESPERA.posicion

Debe ser mayor que cero.

### CERTIFICADO.porcentaje_asistencia

Debe estar entre 0 y 100.

### Fechas

Cuando se comparan fecha/hora de inicio y finalización:

`fecha_fin >= fecha_inicio`

La implementación física definitiva de estas restricciones queda para la
etapa de PostgreSQL.

---

## 18. Auditoría

Las entidades que requieren trazabilidad de cambios de estado utilizan:

- `created_at`
- `updated_at`
- `usuario_id`

`usuario_id` referencia a `usuario.usuario_id`.

La solución registra quién realizó el último cambio relevante.

Un historial completo de todas las transiciones podrá implementarse
posteriormente si el proyecto requiere auditoría histórica detallada.

---

## 19. Fuera de alcance físico

RN-08 establece que:

- no se implementarán pagos reales;
- no se implementará emisión legal de certificados.

Por lo tanto, no se agregan tablas de pagos ni mecanismos de facturación.

---

## 20. Criterio de salida

Este modelo físico se considera preparado para la siguiente etapa cuando:

- los tipos candidatos estén aceptados;
- las nulabilidades principales estén justificadas;
- PK/FK/UQ/CHECK estén definidos;
- las decisiones pendientes críticas estén cerradas;
- el orden de migración no requiera nuevas decisiones importantes.