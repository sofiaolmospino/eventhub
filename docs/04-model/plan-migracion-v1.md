# Plan de Migración V1 — EventHub

## 1. Objetivo

La primera migración debe permitir representar el flujo crítico principal
de EventHub:

Organizador publica evento → participante se inscribe → se controla cupo
y lista de espera → se registra asistencia → se habilita certificado
según criterio.

La migración V1 incluirá las tablas necesarias para representar el núcleo
del flujo y la trazabilidad básica de las operaciones.

Todavía no se ejecutará SQL durante la Clase 05.

---

## 2. Tablas incluidas

La V1 contempla las siguientes tablas:

1. usuario
2. evento
3. sede
4. participante
5. sala
6. sesion_evento
7. inscripcion
8. lista_espera
9. asistencia
10. certificado
11. comunicacion
12. feedback

---

## 3. Orden de creación

### 1. usuario

No depende de otras tablas del modelo.

Se crea primero porque otras entidades utilizarán `usuario_id` para
registrar quién realizó cambios relevantes.

---

### 2. evento

Depende de:

- usuario

FK:

`evento.usuario_id -> usuario.usuario_id`

Por eso se crea después de `usuario`.

---

### 3. sede

No depende de otras tablas del núcleo.

Puede crearse independientemente.

---

### 4. participante

No depende de otras tablas del núcleo.

Puede crearse independientemente.

El atributo `correo` no tendrá UNIQUE en esta versión.

---

### 5. sala

Depende de:

- sede

FK:

`sala.sede_id -> sede.sede_id`

Por eso se crea después de `sede`.

---

### 6. sesion_evento

Depende de:

- evento

FK:

`sesion_evento.evento_id -> evento.evento_id`

Por eso se crea después de `evento`.

---

### 7. inscripcion

Depende de:

- participante
- evento
- usuario

FK:

`inscripcion.participante_id -> participante.participante_id`

`inscripcion.evento_id -> evento.evento_id`

`inscripcion.usuario_id -> usuario.usuario_id`

Por eso estas tablas deben existir antes.

Restricción:

`UNIQUE(participante_id, evento_id)`

---

### 8. lista_espera

Depende de:

- participante
- evento
- usuario

FK:

`lista_espera.participante_id -> participante.participante_id`

`lista_espera.evento_id -> evento.evento_id`

`lista_espera.usuario_id -> usuario.usuario_id`

Restricción:

`UNIQUE(participante_id, evento_id)`

Esta restricción representa la decisión del grupo de que un participante
no puede volver a ingresar a la lista de espera del mismo evento.

---

### 9. asistencia

Depende de:

- participante
- sesion_evento
- usuario

FK:

`asistencia.participante_id -> participante.participante_id`

`asistencia.sesion_evento_id -> sesion_evento.sesion_evento_id`

`asistencia.usuario_id -> usuario.usuario_id`

Por eso se crea después de `participante`, `sesion_evento` y `usuario`.

Restricción:

`UNIQUE(participante_id, sesion_evento_id)`

La asistencia se registra por sesión de evento.

---

### 10. certificado

Depende de:

- participante
- evento
- usuario

FK:

`certificado.participante_id -> participante.participante_id`

`certificado.evento_id -> evento.evento_id`

`certificado.usuario_id -> usuario.usuario_id`

Restricción:

`UNIQUE(participante_id, evento_id)`

Un participante puede tener como máximo un certificado por evento.

---

### 11. comunicacion

Depende de:

- evento

FK:

`comunicacion.evento_id -> evento.evento_id`

Por eso se crea después de `evento`.

La definición de destinatarios individuales queda para una etapa posterior.

---

### 12. feedback

Depende de:

- participante
- evento

FK:

`feedback.participante_id -> participante.participante_id`

`feedback.evento_id -> evento.evento_id`

Restricción:

`UNIQUE(participante_id, evento_id)`

Un participante puede registrar como máximo un feedback para un evento.

---

## 4. Resumen de dependencias

```text
USUARIO
   │
   ├───────────────┐
   ↓               ↓
 EVENTO        PARTICIPANTE
   │               │
   ↓               │
SESION_EVENTO      │
   │               │
   └──────┐        │
          ↓        ↓
       ASISTENCIA

EVENTO ──────────────┐
  │                  │
  ↓                  ↓
INSCRIPCION      LISTA_ESPERA
  │
  │
  └──────────────┐
                 ↓
             CERTIFICADO

EVENTO ──────────→ COMUNICACION

PARTICIPANTE ─────→ FEEDBACK
EVENTO ────────────→ FEEDBACK