# EventHub — Modelo relacional v0.1

## 1. Fuente

- Proyecto oficial: PA-10 — EventHub: Gestión de Eventos, Inscripciones y Control de Asistencia.
- Modelo conceptual base: `model-conceptual-v0.1.md`.
- Flujo crítico utilizado:

**Organizador publica evento → participante se inscribe → sistema controla
cupo/lista de espera → se registra asistencia → se habilita certificado
según criterio.**

El flujo corresponde al definido en la ficha oficial del proyecto.

## 2. Criterios de transformación

- Las relaciones 1:N se representan colocando la FK en el lado N.
- Las relaciones N:M se resuelven mediante una tabla asociativa cuando
  realmente existen.
- La optionalidad se registra antes de decidir NULL/NOT NULL.
- Las claves naturales relevantes se analizan como candidatas a UNIQUE.
- Las PK propuestas inicialmente serán identificadores técnicos estables.
- No se definen todavía tipos físicos de PostgreSQL.
- No se define todavía DDL ni CREATE TABLE.
- Las decisiones que no estén cerradas por la ficha oficial o por el grupo
  se documentan como pendientes en lugar de asumirlas silenciosamente.

## 3. Tablas candidatas núcleo

### usuario

Propósito: representa la identidad que utiliza el sistema y permite
registrar quién realiza operaciones relevantes que requieren trazabilidad.

Atributos iniciales:

- `usuario_id` [PK]
- `nombre`
- `apellido`
- `correo`
- `rol`

Reglas y requisitos relacionados:

- RF-04

---

### evento

Propósito: representa una actividad o evento gestionado por EventHub.

Atributos iniciales:

- `evento_id` [PK]
- `nombre`
- `descripcion`
- `responsable`
- `modalidad`
- `fecha_inicio`
- `fecha_fin`
- `capacidad`
- `estado`
- `usuario_id` [FK → usuario.usuario_id]

Reglas y requisitos relacionados:

- RN-01
- RN-02
- RF-05
- RF-15
- RF-04

---

### sesion_evento

Propósito: representa una sesión o bloque específico que forma parte de
un evento.

Atributos iniciales:

- `sesion_evento_id` [PK]
- `evento_id` [FK → evento.evento_id]
- `fecha_inicio`
- `fecha_fin`

Reglas y requisitos relacionados:

- RF-06
- RN-06

---

### sede

Propósito: representa el lugar físico general donde se desarrollan
eventos.

Atributos iniciales:

- `sede_id` [PK]
- `nombre`
- `direccion`

Reglas y requisitos relacionados:

- RF-07

---

### sala

Propósito: representa un espacio específico dentro de una sede.

Atributos iniciales:

- `sala_id` [PK]
- `sede_id` [FK → sede.sede_id]
- `nombre`
- `capacidad`

Reglas y requisitos relacionados:

- RF-07

---

### participante

Propósito: representa a la persona que consulta eventos y realiza acciones
de participación.

Atributos iniciales:

- `participante_id` [PK]
- `nombre`
- `apellido`
- `correo`

Reglas y requisitos relacionados:

- RF-08
- RF-17

Nota:

El atributo `correo` no se considera UNIQUE. El modelo permite que un mismo
correo pertenezca a más de un participante.

---

### inscripcion

Propósito: representa la inscripción de un participante a un evento.

Atributos iniciales:

- `inscripcion_id` [PK]
- `participante_id` [FK → participante.participante_id]
- `evento_id` [FK → evento.evento_id]
- `fecha_inscripcion`
- `estado`
- `usuario_id` [FK → usuario.usuario_id]

Reglas y requisitos relacionados:

- RN-03
- RN-04
- RF-08
- RF-16
- RF-17
- RF-04

Estados válidos:

- PENDIENTE
- CONFIRMADA
- CANCELADA

Restricción:

- `UNIQUE(participante_id, evento_id)`

---

### lista_espera

Propósito: representa el registro de un participante que espera un cupo
cuando un evento se encuentra lleno y la lista de espera está habilitada.

Atributos iniciales:

- `lista_espera_id` [PK]
- `participante_id` [FK → participante.participante_id]
- `evento_id` [FK → evento.evento_id]
- `fecha_ingreso`
- `posicion`
- `estado`
- `usuario_id` [FK → usuario.usuario_id]

Reglas y requisitos relacionados:

- RN-03
- RN-05
- RF-09
- RF-16
- RF-19
- RF-04

Estados válidos:

- ACTIVA
- PROMOVIDA
- CANCELADA

Restricción:

- `UNIQUE(participante_id, evento_id)`

La restricción evita que un participante vuelva a ingresar a la lista de
espera del mismo evento después de haber sido promovido, cancelado o retirado.

---

### asistencia

Propósito: representa el registro de asistencia de un participante a una
sesión específica de un evento.

Atributos iniciales:

- `asistencia_id` [PK]
- `participante_id` [FK → participante.participante_id]
- `sesion_evento_id` [FK → sesion_evento.sesion_evento_id]
- `fecha_registro`
- `estado`
- `usuario_id` [FK → usuario.usuario_id]

Reglas y requisitos relacionados:

- RN-06
- RF-10
- RF-18
- RF-04

Estados válidos:

- PRESENTE
- AUSENTE

Restricción:

- `UNIQUE(participante_id, sesion_evento_id)`

La asistencia se registra por sesión de evento.

---

### certificado

Propósito: representa el certificado académico simple habilitado para un
participante que cumple el criterio de asistencia.

Atributos iniciales:

- `certificado_id` [PK]
- `participante_id` [FK → participante.participante_id]
- `evento_id` [FK → evento.evento_id]
- `fecha_emision`
- `porcentaje_asistencia`
- `estado`
- `usuario_id` [FK → usuario.usuario_id]

Reglas y requisitos relacionados:

- RN-07
- RF-12
- RF-04

Estados válidos:

- HABILITADO
- EMITIDO
- ANULADO

Restricción:

- `UNIQUE(participante_id, evento_id)`

Un participante puede tener como máximo un certificado por evento.

---

### comunicacion

Propósito: representa una comunicación relacionada con un evento.

Atributos iniciales:

- `comunicacion_id` [PK]
- `evento_id` [FK → evento.evento_id]
- `asunto`
- `contenido`
- `fecha_envio`
- `estado`

Reglas y requisitos relacionados:

- RF-11

La definición de destinatarios individuales queda pendiente para una etapa
posterior.

---

### feedback

Propósito: representa la valoración o comentario de un participante sobre
un evento.

Atributos iniciales:

- `feedback_id` [PK]
- `participante_id` [FK → participante.participante_id]
- `evento_id` [FK → evento.evento_id]
- `valoracion`
- `comentario`
- `fecha`

Reglas y requisitos relacionados:

- RF-13

Restricción:

- `UNIQUE(participante_id, evento_id)`

Un participante puede registrar como máximo un feedback para un evento.

---

## 4. Relaciones

### Usuario — Evento

**Usuario 1:N Evento**

Un usuario puede realizar cambios relevantes en varios eventos.

Cada evento referencia al usuario responsable del último cambio relevante
mediante `evento.usuario_id`.

---

### Usuario — Inscripcion

**Usuario 1:N Inscripcion**

Un usuario puede realizar cambios relevantes en varias inscripciones.

Cada inscripción referencia al usuario que realizó el último cambio relevante
mediante `inscripcion.usuario_id`.

---

### Usuario — ListaEspera

**Usuario 1:N ListaEspera**

Un usuario puede realizar cambios relevantes en varios registros de lista
de espera.

Cada registro referencia al usuario que realizó el último cambio relevante
mediante `lista_espera.usuario_id`.

---

### Usuario — Asistencia

**Usuario 1:N Asistencia**

Un usuario puede registrar o modificar varias asistencias.

Cada asistencia referencia al usuario que realizó el último cambio relevante
mediante `asistencia.usuario_id`.

---

### Usuario — Certificado

**Usuario 1:N Certificado**

Un usuario puede realizar cambios relevantes en varios certificados.

Cada certificado referencia al usuario que realizó el último cambio relevante
mediante `certificado.usuario_id`.

---

### Evento — SesionEvento

**Evento 1:N SesionEvento**

Un evento puede estar compuesto por cero o varias sesiones.

Cada sesión pertenece a un único evento.

La FK `sesion_evento.evento_id` se ubica en el lado N.

Justificación:

- RF-06
- La sesión forma parte de la organización de un evento.

---

### Sede — Sala

**Sede 1:N Sala**

Una sede puede contener cero o varias salas.

Cada sala pertenece a una única sede.

La FK `sala.sede_id` se ubica en el lado N.

Justificación:

- RF-07

---

### Participante — Inscripcion

**Participante 1:N Inscripcion**

Un participante puede tener cero o varias inscripciones.

Cada inscripción corresponde a un único participante.

La FK `inscripcion.participante_id` se ubica en el lado N.

Justificación:

- RF-08
- RF-17

---

### Evento — Inscripcion

**Evento 1:N Inscripcion**

Un evento puede tener cero o varias inscripciones.

Cada inscripción corresponde a un único evento.

La FK `inscripcion.evento_id` se ubica en el lado N.

Justificación:

- RN-03
- RN-04
- RF-16
- RF-17

---

### Participante — ListaEspera

**Participante 1:N ListaEspera**

Un participante puede aparecer en cero o varias listas de espera de
diferentes eventos.

Cada registro de lista de espera corresponde a un único participante.

La FK `lista_espera.participante_id` se ubica en el lado N.

Justificación:

- RN-03
- RN-05
- RF-09

---

### Evento — ListaEspera

**Evento 1:N ListaEspera**

Un evento puede tener cero o varios participantes en lista de espera.

Cada registro de lista de espera corresponde a un único evento.

La FK `lista_espera.evento_id` se ubica en el lado N.

Justificación:

- RN-03
- RN-05
- RF-09
- RF-19

---

### Participante — Asistencia

**Participante 1:N Asistencia**

Un participante puede tener cero o varias asistencias.

Cada registro de asistencia corresponde a un único participante.

La FK `asistencia.participante_id` se ubica en el lado N.

Justificación:

- RN-06
- RF-10
- RF-18

---

### SesionEvento — Asistencia

**SesionEvento 1:N Asistencia**

Una sesión puede tener cero o varias asistencias.

Cada registro de asistencia corresponde a una única sesión.

La FK `asistencia.sesion_evento_id` se ubica en el lado N.

Justificación:

- RN-06
- RF-10
- RF-18

La asistencia se registra específicamente por sesión de evento.

---

### Participante — Certificado

**Participante 1:N Certificado**

Un participante puede tener cero o varios certificados correspondientes a
diferentes eventos.

Cada certificado corresponde a un único participante.

---

### Evento — Certificado

**Evento 1:N Certificado**

Un evento puede generar cero o varios certificados.

Cada certificado corresponde a un único evento.

La combinación participante/evento es única.

---

### Participante — Feedback

**Participante 1:N Feedback**

Un participante puede registrar feedback para diferentes eventos.

Cada feedback corresponde a un único participante.

---

### Evento — Feedback

**Evento 1:N Feedback**

Un evento puede recibir cero o varios feedbacks.

Cada feedback corresponde a un único evento.

La combinación participante/evento es única.

---

## 5. Relaciones N:M

### Participante N:M Evento

La relación conceptual original es:

**Participante N:M Evento**

Un participante puede inscribirse en múltiples eventos y un evento puede
tener múltiples participantes.

La relación se resuelve mediante la entidad asociativa:

**Inscripcion**

Queda:

**Participante 1:N Inscripcion**

**Evento 1:N Inscripcion**

`Inscripcion` posee atributos propios de la relación:

- `fecha_inscripcion`
- `estado`

La resolución permite representar además las reglas relacionadas con cupos,
inscripciones y duplicados.

---

## 6. Claves naturales / UNIQUE

### Inscripcion

Restricción definitiva:

`UNIQUE(participante_id, evento_id)`

RN-04 establece que no puede existir una inscripción duplicada para el mismo
participante y evento.

---

### Participante

El `correo` NO es UNIQUE.

El modelo permite que un mismo correo pertenezca a más de un participante.

---

### ListaEspera

Restricción definitiva:

`UNIQUE(participante_id, evento_id)`

Un participante no puede volver a ingresar a la lista de espera del mismo
evento.

---

### Asistencia

Restricción definitiva:

`UNIQUE(participante_id, sesion_evento_id)`

Un participante no puede tener más de un registro de asistencia para la misma
sesión.

---

### Certificado

Restricción definitiva:

`UNIQUE(participante_id, evento_id)`

Un participante puede tener como máximo un certificado por evento.

---

### Feedback

Restricción definitiva:

`UNIQUE(participante_id, evento_id)`

Un participante puede registrar como máximo un feedback por evento.

---

## 7. Optionalidad

### `sesion_evento.evento_id`

**Obligatoria.**

Una sesión no tiene sentido dentro del modelo sin pertenecer a un evento.

### `sala.sede_id`

**Obligatoria.**

Una sala pertenece a una sede.

### `inscripcion.participante_id`

**Obligatoria.**

Una inscripción siempre corresponde a un participante.

### `inscripcion.evento_id`

**Obligatoria.**

Una inscripción siempre corresponde a un evento.

### `lista_espera.participante_id`

**Obligatoria.**

Cada registro de lista de espera corresponde a un participante.

### `lista_espera.evento_id`

**Obligatoria.**

Cada registro de lista de espera corresponde a un evento.

### `asistencia.participante_id`

**Obligatoria.**

Cada asistencia corresponde a un participante.

### `asistencia.sesion_evento_id`

**Obligatoria.**

Cada asistencia corresponde a una sesión específica.

### `certificado.participante_id`

**Obligatoria.**

Cada certificado corresponde a un participante.

### `certificado.evento_id`

**Obligatoria.**

Cada certificado corresponde a un evento.

### Evento → SesionEvento

**Opcional en el lado Evento.**

Un evento puede existir antes de tener sesiones asociadas.

### Sede → Sala

**Opcional en el lado Sede.**

Una sede puede existir sin que todavía se hayan registrado salas.

### Participante → Inscripcion

**Opcional en el lado Participante.**

Un participante puede existir sin tener inscripciones.

### Evento → Inscripcion

**Opcional en el lado Evento.**

Un evento puede estar publicado sin tener todavía participantes inscritos.

### Participante → Asistencia

**Opcional en el lado Participante.**

Un participante puede existir sin haber asistido todavía a una sesión.

### SesionEvento → Asistencia

**Opcional en el lado SesionEvento.**

Una sesión puede existir antes de registrar asistencias.

### Participante → Certificado

**Opcional en el lado Participante.**

Un participante puede no tener certificados.

### Evento → Certificado

**Opcional en el lado Evento.**

Un evento puede no generar certificados.

---

## 8. Reglas iniciales de integridad

- Un evento debe tener responsable, modalidad, fechas, capacidad y estado.
- Los estados válidos de un evento son:
  - BORRADOR
  - PUBLICADO
  - EN_CURSO
  - FINALIZADO
  - CANCELADO
- La inscripción debe respetar el cupo disponible.
- Si el cupo está lleno y la lista de espera está habilitada, el participante
  puede pasar a la lista de espera.
- No puede existir una inscripción duplicada para el mismo participante y
  evento.
- La promoción desde lista de espera debe ser ordenada y trazable.
- Un participante no puede volver a ingresar a la lista de espera del mismo
  evento.
- La asistencia se registra por sesión de evento.
- Un participante no puede tener dos registros de asistencia para la misma
  sesión.
- El certificado sólo se habilita cuando se cumple el porcentaje mínimo de
  asistencia.
- Un participante puede tener como máximo un certificado por evento.
- Un participante puede registrar como máximo un feedback por evento.
- Las operaciones relevantes que cambian estados deben conservar fecha y
  usuario según RF-04.

---

## 9. Auditoría

Para las operaciones relevantes que cambian estados se utiliza:

- `created_at`
- `updated_at`
- `usuario_id`

`usuario_id` referencia a `usuario.usuario_id` y permite identificar al
usuario que realizó el último cambio relevante.

La auditoría completa de cada transición histórica podrá ampliarse
posteriormente si el proyecto requiere conservar un historial de múltiples
cambios.

---

## 10. Revisión de normalización básica

### Listas multivaluadas detectadas/corregidas

No se almacenarán múltiples participantes, sesiones o salas dentro de una
misma columna.

Por ejemplo, no se utilizarán estructuras como:

- `participantes = "1,2,3"`
- `sesiones = "1,2,3"`

Cada concepto persistente tendrá su propia representación y relación.

### Columnas repetitivas detectadas/corregidas

No se utilizarán columnas como:

- `participante_1`
- `participante_2`
- `participante_3`
- `sesion_1`
- `sesion_2`
- `sesion_3`

Las relaciones se representan mediante FK y entidades asociativas.

### Datos redundantes detectados/corregidos

Los datos del participante no se copiarán dentro de `inscripcion`.

La inscripción referencia al participante mediante `participante_id`.

Los datos de la sede tampoco se repetirán dentro de `sala`; la sala referencia
a su sede mediante `sede_id`.

### Relación N:M

La relación N:M entre `Participante` y `Evento` se resuelve mediante
`Inscripcion`, evitando almacenar listas de participantes dentro de `Evento`.