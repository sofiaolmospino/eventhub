# Plan de Migración V1 — EventHub

## 1. Objetivo

La primera migración debe permitir representar el flujo crítico principal
de EventHub:

Organizador publica evento -> participante se inscribe -> se controla cupo
y lista de espera -> se registra asistencia -> se habilita certificado.

La migración V1 se limitará al núcleo necesario para representar este flujo.

Todavía no se ejecutará SQL durante la Clase 05.

## 2. Tablas incluidas

### Orden propuesto

1. evento
2. sede
3. participante
4. sala
5. sesion_evento
6. inscripcion
7. lista_espera
8. asistencia
9. certificado

## 3. Justificación del orden

### 1. evento

No depende de otra tabla del núcleo.

### 2. sede

No depende de otra tabla del núcleo.

### 3. participante

No depende de otra tabla del núcleo.

### 4. sala

Depende de:

- sede

Por eso se crea después de sede.

### 5. sesion_evento

Depende de:

- evento

Por eso se crea después de evento.

### 6. inscripcion

Depende de:

- participante
- evento

Por eso ambas tablas deben existir antes.

### 7. lista_espera

Depende de:

- participante
- evento

Por eso ambas tablas deben existir antes.

### 8. asistencia

Depende de:

- participante

Su dependencia con evento/sesión queda pendiente de la decisión de RN-06.

### 9. certificado

Depende de:

- participante
- evento

Por eso se crea después de ambos.

## 4. Restricciones previstas

### PK

Cada tabla tendrá una PK técnica BIGINT:

- evento.evento_id
- sede.sede_id
- participante.participante_id
- sala.sala_id
- sesion_evento.sesion_evento_id
- inscripcion.inscripcion_id
- lista_espera.lista_espera_id
- asistencia.asistencia_id
- certificado.certificado_id

### FK

- sala.sede_id -> sede.sede_id
- sesion_evento.evento_id -> evento.evento_id
- inscripcion.participante_id -> participante.participante_id
- inscripcion.evento_id -> evento.evento_id
- lista_espera.participante_id -> participante.participante_id
- lista_espera.evento_id -> evento.evento_id
- asistencia.participante_id -> participante.participante_id
- certificado.participante_id -> participante.participante_id
- certificado.evento_id -> evento.evento_id

La FK definitiva de asistencia hacia evento/sesión queda pendiente.

## 5. NOT NULL

Se aplicará NOT NULL a:

- PK;
- FK obligatorias;
- datos requeridos por RN-01;
- fechas de operaciones;
- estados;
- capacidades;
- atributos necesarios para identificar una relación válida.

Los atributos opcionales podrán permanecer nullable.

## 6. UNIQUE

### Confirmada

`inscripcion(participante_id, evento_id)`

Justificación:

RN-04 impide inscripción duplicada.

### Candidatas

- participante.correo
- lista_espera(participante_id, evento_id)
- certificado(participante_id, evento_id)

Estas no se convierten todavía en restricciones definitivas hasta cerrar las
reglas de negocio correspondientes.

## 7. CHECK candidatos

### Evento

`capacidad > 0`

### Sala

`capacidad > 0`

### Sesión

`fecha_fin > fecha_inicio`

### Lista de espera

`posicion > 0`

### Certificado

`porcentaje_asistencia >= 0`
`porcentaje_asistencia <= 100`

### Estados

Se utilizarán dominios controlados para evitar valores inválidos.

## 8. Reglas que requerirán lógica posterior

### RN-03

Controlar el cupo requiere comparar las inscripciones con la capacidad.

### RN-05

Promover la lista de espera requiere seleccionar participantes según orden
y disponibilidad.

### RN-06

Determinar si la asistencia corresponde a evento o sesión depende de la
configuración.

### RN-07

Habilitar el certificado requiere comprobar el porcentaje de asistencia.

### RF-04

La trazabilidad de usuario y fecha requiere cerrar el modelo de identidad y
auditoría.

## 9. Fuera de V1

Las siguientes tablas quedan fuera de la primera migración:

- comunicacion
- feedback

No se eliminan del modelo definitivo.

Se implementarán posteriormente cuando el núcleo transaccional esté estable.

## 10. Decisiones pendientes antes del DDL

### D-01 — Asistencia

Definir si asistencia referencia:

- evento;
- sesión;
- o ambos mediante una estructura común.

Origen: RN-06.

### D-02 — Auditoría

Definir cómo se registra el usuario responsable de cambios de estado.

Origen: RF-04.

### D-03 — Estado de inscripción

Cerrar el conjunto oficial de estados y sus transiciones.

Origen: RF-08.

### D-04 — Estado de lista de espera

Cerrar los estados y transiciones de promoción.

Origen: RN-05 y RF-19.

### D-05 — Unicidad de certificado

Definir si un participante puede tener como máximo un certificado por evento.

Origen: RF-12 y RF-20.

## 11. Prueba contra el flujo crítico

### Paso 1 — Organizador publica evento

Tabla principal:

`evento`

Datos necesarios:

- nombre
- responsable
- modalidad
- fechas
- capacidad
- estado

Reglas:

- RN-01
- RN-02
- RF-15

### Paso 2 — Participante se inscribe

Tablas:

- participante
- evento
- inscripcion

La inscripción referencia al participante y al evento.

Reglas:

- RN-03
- RN-04
- RF-08
- RF-16
- RF-17

### Paso 3 — Se controla cupo/lista de espera

Tablas:

- inscripcion
- lista_espera
- evento

El sistema debe comparar el número de inscripciones con la capacidad.

Si corresponde, utiliza lista de espera.

Reglas:

- RN-03
- RN-05
- RF-16
- RF-19

### Paso 4 — Se registra asistencia

Tabla:

- asistencia

La referencia exacta a evento/sesión queda pendiente por RN-06.

Reglas:

- RN-06
- RF-10
- RF-18

### Paso 5 — Se habilita certificado

Tablas:

- asistencia
- certificado
- participante
- evento

El certificado se habilita únicamente si se cumple el porcentaje mínimo.

Reglas:

- RN-07
- RF-12
- RF-20

## 12. Criterio de salida de V1

La migración V1 estará preparada para DDL cuando:

- las tablas tengan nombres físicos definitivos;
- los tipos candidatos estén aprobados;
- PK/FK estén cerradas;
- UNIQUE necesarias estén confirmadas;
- CHECK simples estén identificados;
- nulabilidad esté justificada;
- asistencia tenga una referencia definitiva;
- auditoría tenga una estrategia definida;
- el orden de creación no requiera nuevas decisiones importantes.