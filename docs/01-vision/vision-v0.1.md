# Backlog v0.1 — EventHub

## 1. Propósito

Este backlog organiza las funcionalidades iniciales de EventHub y mantiene su
trazabilidad con los requisitos funcionales y reglas de negocio oficiales del
proyecto PA-10.

## 2. Historias de usuario

### HU-01 — Autenticación y permisos

**Como** usuario del sistema,
**quiero** iniciar sesión y acceder según mi rol,
**para** utilizar únicamente las funcionalidades autorizadas.

**Prioridad:** Alta

**Trazabilidad:** RF-01

---

### HU-02 — Gestionar eventos

**Como** organizador,
**quiero** crear, consultar y actualizar eventos,
**para** administrar las actividades disponibles en EventHub.

**Prioridad:** Alta

**Trazabilidad:** RF-05

**Reglas relacionadas:** RN-01, RN-02

---

### HU-03 — Publicar evento

**Como** organizador,
**quiero** publicar un evento válido,
**para** permitir que los participantes puedan consultarlo e inscribirse.

**Prioridad:** Alta

**Trazabilidad:** RF-15

**Reglas relacionadas:** RN-01, RN-02

---

### HU-04 — Gestionar sesiones

**Como** organizador o facilitador,
**quiero** gestionar las sesiones de un evento,
**para** organizar las actividades que forman parte del evento.

**Prioridad:** Alta

**Trazabilidad:** RF-06

---

### HU-05 — Gestionar sedes y salas

**Como** organizador,
**quiero** administrar sedes y salas,
**para** definir los espacios donde se desarrollan las actividades.

**Prioridad:** Alta

**Trazabilidad:** RF-07

---

### HU-06 — Gestionar participantes

**Como** administrador u organizador,
**quiero** consultar y gestionar participantes,
**para** mantener la información necesaria para las inscripciones y asistencia.

**Prioridad:** Alta

**Trazabilidad:** RF-08

---

### HU-07 — Inscribirse a un evento

**Como** participante,
**quiero** inscribirme a un evento,
**para** confirmar mi participación.

**Prioridad:** Alta

**Trazabilidad:** RF-08, RF-17

**Reglas relacionadas:** RN-03, RN-04

---

### HU-08 — Controlar cupos

**Como** sistema,
**quiero** validar la disponibilidad de cupos antes de aceptar una inscripción,
**para** evitar superar la capacidad del evento.

**Prioridad:** Alta

**Trazabilidad:** RF-16

**Reglas relacionadas:** RN-03

---

### HU-09 — Gestionar lista de espera

**Como** participante,
**quiero** ingresar a una lista de espera cuando un evento está lleno,
**para** tener la posibilidad de acceder si se libera un cupo.

**Prioridad:** Alta

**Trazabilidad:** RF-09, RF-16

**Reglas relacionadas:** RN-03

---

### HU-10 — Evitar inscripciones duplicadas

**Como** sistema,
**quiero** impedir que un participante se inscriba más de una vez al mismo evento,
**para** mantener la información consistente.

**Prioridad:** Alta

**Trazabilidad:** RF-17

**Reglas relacionadas:** RN-04

---

### HU-11 — Promover lista de espera

**Como** organizador,
**quiero** promover participantes desde la lista de espera cuando exista disponibilidad,
**para** mantener el orden y la trazabilidad de la asignación de cupos.

**Prioridad:** Alta

**Trazabilidad:** RF-19

**Reglas relacionadas:** RN-05

---

### HU-12 — Registrar asistencia

**Como** facilitador,
**quiero** registrar la asistencia de los participantes,
**para** mantener evidencia de su participación.

**Prioridad:** Alta

**Trazabilidad:** RF-10, RF-18

**Reglas relacionadas:** RN-06

---

### HU-13 — Gestionar comunicaciones

**Como** organizador,
**quiero** enviar comunicaciones relacionadas con un evento,
**para** informar a los participantes sobre novedades o información importante.

**Prioridad:** Media

**Trazabilidad:** RF-11

---

### HU-14 — Habilitar certificado

**Como** sistema,
**quiero** verificar el porcentaje de asistencia antes de habilitar un certificado,
**para** evitar certificados que no cumplan el criterio establecido.

**Prioridad:** Media

**Trazabilidad:** RF-12, RF-20

**Reglas relacionadas:** RN-07

---

### HU-15 — Registrar feedback

**Como** participante,
**quiero** proporcionar feedback sobre un evento,
**para** expresar mi valoración y opinión sobre la experiencia.

**Prioridad:** Media

**Trazabilidad:** RF-13

---

### HU-16 — Consultar reportes

**Como** administrador u organizador,
**quiero** consultar indicadores de eventos, inscripciones y asistencia,
**para** analizar la operación de la plataforma.

**Prioridad:** Media

**Trazabilidad:** RF-14

---

### HU-17 — Validar datos

**Como** sistema,
**quiero** validar los datos obligatorios tanto en cliente como en servidor,
**para** evitar información inválida.

**Prioridad:** Alta

**Trazabilidad:** RF-03

---

### HU-18 — Registrar trazabilidad

**Como** sistema,
**quiero** registrar fecha y usuario en las operaciones que cambian estados,
**para** mantener trazabilidad de los procesos.

**Prioridad:** Alta

**Trazabilidad:** RF-04

## 3. Flujo crítico del MVP

El flujo prioritario del proyecto es:

1. Organizador publica evento.
2. Participante consulta el evento.
3. Participante se inscribe.
4. Sistema verifica el cupo.
5. Si existe disponibilidad, confirma la inscripción.
6. Si no existe disponibilidad y la lista está habilitada, registra al participante
   en lista de espera.
7. Cuando corresponde, el sistema permite la promoción desde la lista de espera.
8. Facilitador registra asistencia.
9. El sistema verifica el criterio de asistencia.
10. El sistema habilita el certificado cuando corresponde.

## 4. Priorización inicial

### Alta

* Autenticación y permisos.
* Gestión de eventos.
* Publicación de eventos.
* Gestión de sesiones.
* Gestión de sedes y salas.
* Inscripciones.
* Control de cupos.
* Lista de espera.
* Evitar duplicados.
* Registro de asistencia.
* Validaciones.
* Trazabilidad.

### Media

* Comunicaciones.
* Certificados.
* Feedback.
* Reportes.

## 5. Criterio de aceptación general

Una funcionalidad se considera lista cuando:

* cumple el requisito funcional asociado;
* respeta las reglas de negocio correspondientes;
* valida los datos necesarios;
* mantiene la información persistida cuando corresponde;
* presenta resultados y errores de forma comprensible;
* cuenta con evidencia o prueba cuando la funcionalidad contiene lógica relevante;
* mantiene actualizada la documentación afectada.

## 6. Reglas de negocio asociadas

* RN-01: información obligatoria del evento.
* RN-02: estados válidos del evento.
* RN-03: control de cupos y lista de espera.
* RN-04: evitar inscripciones duplicadas.
* RN-05: promoción ordenada y trazable.
* RN-06: registro de asistencia.
* RN-07: criterio para habilitar certificados.
* RN-08: pagos reales y emisión legal de certificados fuera del alcance.