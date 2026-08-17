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

## 2. Estrategia de identificadores

Se propone utilizar `BIGINT` autogenerado para las PK técnicas.

Las PK técnicas identifican filas y no sustituyen las claves naturales o
unicidades de negocio.

## 3. EVENTO

Propósito:

Representa un evento académico que puede ser publicado y utilizado para
gestionar inscripciones, asistencia y certificado.

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
| created_at   | TIMESTAMPTZ    | NO   | Auditoría candidata | RF-04        |
| updated_at   | TIMESTAMPTZ    | NO   | Auditoría candidata | RF-04        |

### Decisiones

`descripcion` puede ser NULL porque un evento puede ser válido aunque todavía
no tenga una descripción extensa.

`capacidad` es obligatoria y debe ser positiva.

`estado` utiliza un conjunto cerrado de valores.

### Regla que no se resuelve sólo con constraint simple

RN-03 requiere controlar el número de inscripciones frente a la capacidad
del evento.

Esto requiere lógica entre múltiples filas.

---

## 4. SESION_EVENTO

Propósito:

Representa una sesión concreta perteneciente a un evento.

| Columna          | Tipo candidato | NULL | Rol/Restricción        | Fuente |
|------------------|----------------|------|------------------------|--------|
| sesion_evento_id | BIGINT         | NO   | PK                     | Diseño |
| evento_id        | BIGINT         | NO   | FK -> evento.evento_id | RF-06  |
| fecha_inicio     | TIMESTAMPTZ    | NO   | NN                     | RF-06  |
| fecha_fin        | TIMESTAMPTZ    | NO   | NN, CHECK fin > inicio | RF-06  |
| created_at       | TIMESTAMPTZ    | NO   | Auditoría candidata    | RF-04  |
| updated_at       | TIMESTAMPTZ    | NO   | Auditoría candidata    | RF-04  |

### Decisiones

Se utiliza TIMESTAMPTZ porque interesa el instante concreto de inicio y
finalización de la sesión.

`evento_id` es obligatorio porque una sesión no tiene sentido sin un evento.

### Regla que no se resuelve sólo con constraint simple

Las transiciones de estado o reglas de solapamiento que eventualmente se
definan requieren lógica adicional.

---

## 5. SEDE

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

## 6. SALA

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

## 7. PARTICIPANTE

Propósito:

Representa al usuario externo o beneficiario que consulta eventos y realiza
acciones de autoservicio autorizadas.

| Columna         | Tipo candidato | NULL | Rol/Restricción     | Fuente       |
|-----------------|----------------|------|---------------------|--------------|
| participante_id | BIGINT         | NO   | PK                  | Diseño       |
| nombre          | VARCHAR(100)   | NO   | NN                  | RF-08        |
| apellido        | VARCHAR(100)   | NO   | NN                  | RF-08        |
| correo          | VARCHAR(254)   | NO   | NN, UQ candidata    | RF-01, RF-08 |
| created_at      | TIMESTAMPTZ    | NO   | Auditoría candidata | RF-04        |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría candidata | RF-04        |

### Decisiones

`correo` se considera candidato a UNIQUE porque puede utilizarse para
identificar una cuenta, pero la unicidad definitiva debe confirmarse con el
modelo de autenticación.

No se convierte en PK porque es un dato de negocio que puede cambiar.

---

## 8. INSCRIPCION

Propósito:

Representa la inscripción de un participante a un evento.

| Columna           | Tipo candidato | NULL | Rol/Restricción                    | Fuente |
|-------------------|----------------|------|------------------------------------|--------|
| inscripcion_id    | BIGINT         | NO   | PK                                 | Diseño |
| participante_id   | BIGINT         | NO   | FK -> participante.participante_id | RF-08  |
| evento_id         | BIGINT         | NO   | FK -> evento.evento_id             | RF-08  |
| fecha_inscripcion | TIMESTAMPTZ    | NO   | NN                                 | RF-08  |
| estado            | VARCHAR(30)    | NO   | NN, dominio controlado             | RF-08  |
| created_at        | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |
| updated_at        | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |

### Restricción principal

UNIQUE(participante_id, evento_id)

### Decisión

La unicidad es contextual.

Un participante puede inscribirse en muchos eventos, pero no puede tener dos
inscripciones para el mismo evento.

Esto implementa RN-04 y RF-17.

### Regla que no se resuelve sólo con constraint simple

RN-03:

El sistema debe respetar la capacidad del evento y utilizar lista de espera
cuando corresponda.

Esto requiere comparar múltiples inscripciones con la capacidad del evento.

---

## 9. LISTA_ESPERA

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
| created_at      | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |

### Decisiones

`posicion` es un entero positivo porque representa el orden dentro de la lista.

No se fija todavía UNIQUE(participante_id, evento_id) porque falta cerrar si un
participante puede volver a entrar a la lista del mismo evento.

### Regla que no se resuelve sólo con constraint simple

RN-05:

La promoción debe ser ordenada y trazable.

La selección del siguiente participante requiere lógica transaccional.

---

## 10. ASISTENCIA

Propósito:

Representa el registro de asistencia de un participante.

| Columna         | Tipo candidato | NULL | Rol/Restricción                    | Fuente |
|-----------------|----------------|------|------------------------------------|--------|
| asistencia_id   | BIGINT         | NO   | PK                                 | Diseño |
| participante_id | BIGINT         | NO   | FK -> participante.participante_id | RF-10  |
| fecha_registro  | TIMESTAMPTZ    | NO   | NN                                 | RF-18  |
| estado          | VARCHAR(30)    | NO   | NN, dominio controlado             | RF-10  |
| created_at      | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |

### Decisión pendiente importante

RN-06 indica que la asistencia se registra por sesión o por evento según
configuración.

Por lo tanto, todavía debe decidirse si la asistencia tendrá:

- evento_id;
- sesion_evento_id;
- o una estructura que soporte ambos escenarios.

No se inventa una FK definitiva mientras esa decisión siga abierta.

### Regla que no se resuelve sólo con constraint simple

El criterio de asistencia depende del contexto del evento/sesión y puede
participar posteriormente en el cálculo del certificado.

---

## 11. CERTIFICADO

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
| created_at            | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |
| updated_at            | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |

### Decisión pendiente

Debe confirmarse si sólo puede existir un certificado por participante/evento.

Mientras no se confirme, no se fija UNIQUE(participante_id, evento_id).

### Regla que no se resuelve sólo con constraint simple

RN-07:

El certificado sólo se habilita cuando se cumple el porcentaje mínimo de
asistencia.

Esto depende de información relacionada con la asistencia.

---

## 12. COMUNICACION

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
| created_at      | TIMESTAMPTZ    | NO   | Auditoría candidata    | RF-04  |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría candidata    | RF-04  |

### Decisión pendiente

Debe definirse si la comunicación se dirige a todos los participantes de un
evento o si necesita destinatarios individuales.

---

## 13. FEEDBACK

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
| created_at      | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |
| updated_at      | TIMESTAMPTZ    | NO   | Auditoría candidata                | RF-04  |

### Decisión pendiente

Debe confirmarse la escala de valoración y si un participante puede registrar
más de un feedback para el mismo evento.

---

## 14. Reglas transaccionales identificadas

Las siguientes reglas no se reducen a un CHECK simple:

### RN-03 — Control de cupo

Debe comparar inscripciones válidas con la capacidad del evento.

### RN-05 — Promoción de lista de espera

Debe seleccionar participantes de manera ordenada y trazable.

### RN-06 — Registro de asistencia

La estructura depende de si el control es por evento o sesión.

### RN-07 — Certificado

Debe verificar el porcentaje mínimo de asistencia antes de habilitarlo.

### RF-04 — Auditoría

Las operaciones de cambio de estado requieren conservar fecha y usuario.

La estructura definitiva de identidad/auditoría queda pendiente.

## 15. Brechas identificadas

### Auditoría de usuario

RF-04 exige registrar fecha y usuario en operaciones que cambian estados.

El modelo actual contiene timestamps candidatos, pero todavía no existe una
entidad de identidad/auditoría definida en el núcleo del modelo.

Debe resolverse antes de la implementación definitiva.

### Asistencia

Debe definirse la referencia a evento o sesión.

### Estados

Los conjuntos exactos de estados de inscripción, lista de espera,
asistencia, certificado, comunicación y feedback deben cerrarse antes del DDL
definitivo.

## 16. Fuera de alcance físico

RN-08 establece que:

- no se implementarán pagos reales;
- no se implementará emisión legal de certificados.

Por lo tanto, no se agregan tablas de pagos ni mecanismos de facturación.

## 17. Criterio de salida

Este modelo físico se considera preparado para la siguiente etapa cuando:

- los tipos candidatos estén aceptados;
- las nulabilidades principales estén justificadas;
- PK/FK/UQ/CHECK estén definidos;
- las decisiones pendientes críticas estén cerradas;
- el orden de migración no requiera nuevas decisiones importantes.