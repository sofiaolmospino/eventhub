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
- Las decisiones que no estén cerradas por la ficha oficial se documentan
  como pendientes en lugar de asumirlas silenciosamente.

## 3. Tablas candidatas núcleo

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

Reglas y requisitos relacionados:

- RN-01
- RN-02
- RF-05
- RF-15

---

### sesion_evento

Propósito: representa una sesión o bloque específico que forma parte de
un evento.

Atributos iniciales:

- `sesion_evento_id` [PK]
- `evento_id` [FK → evento.evento_id]
- `fecha`
- `hora_inicio`
- `hora_fin`

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

---

### inscripcion

Propósito: representa la inscripción de un participante a un evento.

Atributos iniciales:

- `inscripcion_id` [PK]
- `participante_id` [FK → participante.participante_id]
- `evento_id` [FK → evento.evento_id]
- `fecha_inscripcion`
- `estado`

Reglas y requisitos relacionados:

- RN-03
- RN-04
- RF-08
- RF-16
- RF-17

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

Reglas y requisitos relacionados:

- RN-03
- RN-05
- RF-09
- RF-16
- RF-19

---

### asistencia

Propósito: representa el registro de asistencia de un participante a un
evento o sesión.

Atributos iniciales propuestos:

- `asistencia_id` [PK]
- `participante_id` [FK → participante.participante_id]
- `fecha_registro`
- `estado`

La referencia concreta al evento o a la sesión queda como decisión pendiente,
porque RN-06 establece que la asistencia puede registrarse por sesión o por
evento según configuración.

Reglas y requisitos relacionados:

- RN-06
- RF-10
- RF-18

## 4. Relaciones

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

La relación de asistencia con `evento` o `sesion_evento` queda pendiente
de definición debido a RN-06.

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

## 6. Claves naturales / UNIQUE candidatas

### Inscripcion

Candidata:

`(participante_id, evento_id)`

Justificación:

RN-04 establece que no puede existir una inscripción duplicada para el mismo
participante y evento.

Por lo tanto, la combinación de ambos identificadores debe ser única.

### Participante

El `correo` puede ser una candidata a UNIQUE, pero su unicidad definitiva
queda pendiente de confirmar como regla explícita del dominio.

No se agrega una restricción definitiva solamente porque parezca conveniente.

### ListaEspera

La combinación:

`(participante_id, evento_id)`

se considera candidata a UNIQUE, pero queda pendiente de confirmar qué ocurre
si un participante vuelve a entrar en lista de espera después de ser promovido
o cancelar su participación.

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

Un participante puede existir sin haber asistido todavía a un evento.

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
- La asistencia se registra por sesión o evento según configuración.
- El certificado académico simple sólo se habilita cuando se cumple el
  porcentaje mínimo de asistencia.
- Las operaciones relevantes que cambian estados deben conservar trazabilidad
  según las reglas definidas para el proyecto.

## 9. Decisiones pendientes

### D-REL-01 — Registro de asistencia

RN-06 indica que la asistencia puede registrarse por sesión o por evento
según configuración.

Todavía debe definirse si el modelo físico utilizará:

- una FK hacia `evento`;
- una FK hacia `sesion_evento`;
- o una estructura que permita representar ambos escenarios.

No se fija todavía una solución física definitiva.

### D-REL-02 — Unicidad de lista de espera

Debe confirmarse si un participante puede volver a ingresar a la lista de
espera del mismo evento después de haber sido promovido, cancelado o retirado.

Hasta cerrar esta regla, `(participante_id, evento_id)` se mantiene como
candidata a UNIQUE y no como restricción definitiva.

### D-REL-03 — Certificado

`Certificado` forma parte del modelo mínimo esperado y del flujo crítico, pero
no se incluye dentro de las ocho tablas núcleo seleccionadas para este primer
ejercicio.

Se incorporará en la consolidación posterior del modelo relacional.

### D-REL-04 — Comunicacion y Feedback

`Comunicacion` y `Feedback` forman parte del alcance oficial, pero se dejan
fuera del núcleo inicial de ocho tablas porque no son necesarios para explicar
el recorrido principal de publicación, inscripción, cupo/lista de espera y
asistencia.

Se revisarán al completar el modelo relacional.

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