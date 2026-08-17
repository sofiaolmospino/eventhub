# Decisiones de integridad v0.1 — EventHub

## 1. Propósito

Este documento relaciona las reglas de negocio y requisitos funcionales
relevantes de PA-10 con los mecanismos de protección previstos en el modelo
lógico.

No todas las reglas pueden resolverse únicamente mediante PK, FK, UQ o NN.
Las reglas que dependen de varias filas, estados, permisos o procesos deberán
reforzarse posteriormente mediante lógica de backend y, cuando corresponda,
mediante restricciones de base de datos.

## 2. Decisiones de integridad

| RN/RF | Regla | Protección prevista | Justificación |
|---|---|---|---|
| RN-01 | Un evento debe tener responsable, modalidad, fechas, capacidad y estado. | NN | Los datos son necesarios para considerar válido un evento. |
| RN-02 | El estado del evento debe pertenecer al conjunto oficial. | CHECK/backend | El estado sólo puede ser BORRADOR, PUBLICADO, EN_CURSO, FINALIZADO o CANCELADO. |
| RN-03 | La inscripción debe respetar el cupo. | Backend + validación de datos | Requiere comparar las inscripciones existentes con la capacidad del evento. |
| RN-03 | Si se llena el cupo puede utilizarse lista de espera. | Backend + FK | La relación se representa mediante LISTA_ESPERA y la decisión de promoción requiere lógica de negocio. |
| RN-04 | No puede existir inscripción duplicada por participante/evento. | UQ | `UNIQUE(participante_id, evento_id)` evita duplicados estructurales. |
| RN-05 | La promoción desde lista de espera debe ser ordenada y trazable. | Backend + datos de posición/fecha | El orden y la promoción dependen del estado y de varias filas. |
| RN-06 | La asistencia se registra por sesión o evento según configuración. | Backend + FK pendiente | La estructura física todavía debe resolver la alternativa entre evento y sesión. |
| RN-07 | El certificado sólo se habilita al cumplir el porcentaje mínimo de asistencia. | Backend + CHECK para rango | La condición depende de la asistencia; el porcentaje debe estar entre 0 y 100. |
| RF-03 | Validar datos obligatorios en cliente y servidor. | NN + backend | La base de datos protege obligatoriedad estructural y backend realiza validaciones de negocio. |
| RF-04 | Registrar fecha y usuario en operaciones que cambian estados. | Auditoría/backend | La trazabilidad depende del contexto de la operación y no sólo de una FK. |
| RF-17 | Evitar inscripciones duplicadas. | UQ | La combinación participante/evento identifica una inscripción única. |
| RF-18 | Registrar asistencia. | FK + backend | La asistencia debe referenciar participantes válidos y respetar RN-06. |
| RF-19 | Promover lista de espera. | Backend | La promoción requiere determinar orden, disponibilidad y cambio de estado. |
| RF-20 | Habilitar certificado sólo cuando se cumple el criterio. | Backend | La condición depende del porcentaje de asistencia registrado. |

## 3. Constraints estructurales

### PK

Cada tabla tendrá una PK técnica estable:

- EVENTO.evento_id
- SESION_EVENTO.sesion_evento_id
- SEDE.sede_id
- SALA.sala_id
- PARTICIPANTE.participante_id
- INSCRIPCION.inscripcion_id
- LISTA_ESPERA.lista_espera_id
- ASISTENCIA.asistencia_id
- COMUNICACION.comunicacion_id
- CERTIFICADO.certificado_id
- FEEDBACK.feedback_id

### FK

Las relaciones 1:N se protegerán mediante FK:

- SESION_EVENTO.evento_id -> EVENTO.evento_id
- SALA.sede_id -> SEDE.sede_id
- INSCRIPCION.participante_id -> PARTICIPANTE.participante_id
- INSCRIPCION.evento_id -> EVENTO.evento_id
- LISTA_ESPERA.participante_id -> PARTICIPANTE.participante_id
- LISTA_ESPERA.evento_id -> EVENTO.evento_id
- ASISTENCIA.participante_id -> PARTICIPANTE.participante_id
- COMUNICACION.evento_id -> EVENTO.evento_id
- CERTIFICADO.participante_id -> PARTICIPANTE.participante_id
- CERTIFICADO.evento_id -> EVENTO.evento_id
- FEEDBACK.participante_id -> PARTICIPANTE.participante_id
- FEEDBACK.evento_id -> EVENTO.evento_id

La relación definitiva de ASISTENCIA con EVENTO/SESION_EVENTO queda pendiente
por RN-06.

### UNIQUE

Se define como restricción principal:

`UNIQUE(participante_id, evento_id)` en INSCRIPCION.

Esta restricción protege RN-04.

Quedan como candidatas pendientes:

- PARTICIPANTE.correo
- LISTA_ESPERA(participante_id, evento_id)
- CERTIFICADO(participante_id, evento_id)
- FEEDBACK(participante_id, evento_id)

No se convierten en restricciones definitivas hasta cerrar las reglas de
negocio correspondientes.

### NOT NULL / NN

Se propone obligatoriedad para:

- PK de todas las tablas.
- FK que representan relaciones obligatorias.
- Datos que RN-01 exige para EVENTO.
- Fechas, estados e identificadores necesarios para INSCRIPCION,
  LISTA_ESPERA y ASISTENCIA.

Los atributos opcionales se mantienen como tales cuando su ausencia puede
representar un estado válido.

## 4. Dominios y restricciones simples

### EVENTO.estado

Debe pertenecer al conjunto:

- BORRADOR
- PUBLICADO
- EN_CURSO
- FINALIZADO
- CANCELADO

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

La implementación física de estas restricciones queda para la etapa de
PostgreSQL.

## 5. Reglas que requieren lógica transaccional/backend

Las siguientes reglas no pueden protegerse correctamente sólo mediante
constraints simples:

### RN-03 — Control de cupo

Es necesario comparar la cantidad de inscripciones válidas con la capacidad
del evento.

### RN-05 — Promoción de lista de espera

La promoción depende de disponibilidad y del orden de los participantes.

### RN-06 — Registro de asistencia

La forma de registrar asistencia depende de si la configuración trabaja por
evento o por sesión.

### RN-07 — Habilitación del certificado

La habilitación depende de calcular o verificar el porcentaje de asistencia.

### RF-04 — Trazabilidad

Registrar el usuario y momento de una transición requiere contexto de la
operación.

## 6. Prueba de contradicción

### RN-04 — Inscripción duplicada

**Dato inválido posible:**

```text
participante_id = 15
evento_id = 8