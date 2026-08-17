# Diccionario de datos v0.1 — EventHub

## 1. Propósito

Este documento describe el significado de los atributos del modelo lógico
de EventHub, su obligatoriedad, función estructural, dominio preliminar y
trazabilidad con los requisitos funcionales y reglas de negocio.

Todavía no se definen tipos físicos de PostgreSQL.

## 2. EVENTO

| Campo        | Significado                      | Obligatorio | PK/FK/UQ | Dominio/regla                                        | Origen       |
|--------------|----------------------------------|-------------|----------|------------------------------------------------------|--------------|
| evento_id    | Identificador estable del evento | Sí          | PK       | Identificador único                                  | RF-05        |
| nombre       | Nombre visible del evento        | Sí          | NN       | No vacío                                             | RN-01        |
| descripcion  | Descripción del evento           | No          | —        | Texto opcional                                       | RF-05        |
| responsable  | Responsable del evento           | Sí          | NN       | Debe existir                                         | RN-01        |
| modalidad    | Modalidad del evento             | Sí          | NN       | Valor permitido por el dominio                       | RN-01        |
| fecha_inicio | Inicio del evento                | Sí          | NN       | Fecha válida                                         | RN-01        |
| fecha_fin    | Fin del evento                   | Sí          | NN       | Debe ser compatible con fecha_inicio                 | RN-01        |
| capacidad    | Cantidad máxima de participantes | Sí          | NN       | Valor positivo                                       | RN-01, RN-03 |
| estado       | Estado actual del evento         | Sí          | NN       | BORRADOR, PUBLICADO, EN_CURSO, FINALIZADO, CANCELADO | RN-02        |

## 3. SESION_EVENTO

| Campo            | Significado                       | Obligatorio | PK/FK/UQ | Dominio/regla              | Origen |
|------------------|-----------------------------------|-------------|----------|----------------------------|--------|
| sesion_evento_id | Identificador de la sesión        | Sí          | PK       | Identificador único        | RF-06  |
| evento_id        | Evento al que pertenece la sesión | Sí          | FK, NN   | Debe referenciar un evento | RF-06  |
| fecha            | Fecha de la sesión                | Sí          | NN       | Fecha válida               | RF-06  |
| hora_inicio      | Hora de inicio                    | Sí          | NN       | Hora válida                | RF-06  |
| hora_fin         | Hora de finalización              | Sí          | NN       | Posterior al inicio        | RF-06  |

## 4. SEDE

| Campo     | Significado              | Obligatorio | PK/FK/UQ | Dominio/regla       | Origen |
|-----------|--------------------------|-------------|----------|---------------------|--------|
| sede_id   | Identificador de la sede | Sí          | PK       | Identificador único | RF-07  |
| nombre    | Nombre de la sede        | Sí          | NN       | No vacío            | RF-07  |
| direccion | Dirección de la sede     | Sí          | NN       | Dirección válida    | RF-07  |

## 5. SALA

| Campo     | Significado                        | Obligatorio | PK/FK/UQ | Dominio/regla             | Origen |
|-----------|------------------------------------|-------------|----------|---------------------------|--------|
| sala_id   | Identificador de la sala           | Sí          | PK       | Identificador único       | RF-07  |
| sede_id   | Sede a la que pertenece            | Sí          | FK, NN   | Debe referenciar una sede | RF-07  |
| nombre    | Nombre o identificación de la sala | Sí          | NN       | No vacío                  | RF-07  |
| capacidad | Capacidad de la sala               | Sí          | NN       | Valor positivo            | RF-07  |

## 6. PARTICIPANTE

| Campo           | Significado                    | Obligatorio | PK/FK/UQ | Dominio/regla            | Origen       |
|-----------------|--------------------------------|-------------|----------|--------------------------|--------------|
| participante_id | Identificador del participante | Sí          | PK       | Identificador único      | RF-08        |
| nombre          | Nombre del participante        | Sí          | NN       | No vacío                 | RF-08        |
| apellido        | Apellido del participante      | Sí          | NN       | No vacío                 | RF-08        |
| correo          | Correo del participante        | Sí          | NN       | Formato de correo válido | RF-01, RF-08 |

La unicidad de `correo` queda como candidata a UQ y no como decisión
definitiva hasta confirmar la regla de negocio correspondiente.

## 7. INSCRIPCION

| Campo             | Significado                     | Obligatorio | PK/FK/UQ | Dominio/regla                    | Origen       |
|-------------------|---------------------------------|-------------|----------|----------------------------------|--------------|
| inscripcion_id    | Identificador de la inscripción | Sí          | PK       | Identificador único              | RF-08        |
| participante_id   | Participante inscrito           | Sí          | FK, NN   | Debe existir                     | RF-08, RF-17 |
| evento_id         | Evento al que se inscribe       | Sí          | FK, NN   | Debe existir                     | RF-08, RF-16 |
| fecha_inscripcion | Momento en que se registra      | Sí          | NN       | Fecha válida                     | RF-08        |
| estado            | Estado de la inscripción        | Sí          | NN       | Valores definidos por el dominio | RF-08        |

### Restricción compuesta

`UQ(participante_id, evento_id)`

Justificación: RN-04 impide inscripciones duplicadas.

## 8. LISTA_ESPERA

| Campo           | Significado                          | Obligatorio | PK/FK/UQ | Dominio/regla                    | Origen |
|-----------------|--------------------------------------|-------------|----------|----------------------------------|--------|
| lista_espera_id | Identificador del registro de espera | Sí          | PK       | Identificador único              | RF-09  |
| participante_id | Participante en espera               | Sí          | FK, NN   | Debe existir                     | RF-09  |
| evento_id       | Evento para el cual espera           | Sí          | FK, NN   | Debe existir                     | RF-09  |
| fecha_ingreso   | Momento de ingreso a la lista        | Sí          | NN       | Fecha válida                     | RN-05  |
| posicion        | Posición en la lista                 | Sí          | NN       | Valor positivo                   | RN-05  |
| estado          | Estado del registro de espera        | Sí          | NN       | Valores definidos por el dominio | RN-05  |

`(participante_id, evento_id)` es candidata a UQ, pero queda pendiente por
la posibilidad de reingreso.

## 9. ASISTENCIA

| Campo           | Significado                              | Obligatorio | PK/FK/UQ | Dominio/regla                    | Origen       |
|-----------------|------------------------------------------|-------------|----------|----------------------------------|--------------|
| asistencia_id   | Identificador de la asistencia           | Sí          | PK       | Identificador único              | RF-10        |
| participante_id | Participante cuya asistencia se registra | Sí          | FK, NN   | Debe existir                     | RF-10, RF-18 |
| fecha_registro  | Fecha del registro de asistencia         | Sí          | NN       | Fecha válida                     | RF-18        |
| estado          | Situación del registro de asistencia     | Sí          | NN       | Valores definidos por el dominio | RN-06        |

La referencia hacia EVENTO o SESION_EVENTO queda pendiente de resolución por
RN-06.

## 10. COMUNICACION

| Campo           | Significado                      | Obligatorio | PK/FK/UQ | Dominio/regla                    | Origen |
|-----------------|----------------------------------|-------------|----------|----------------------------------|--------|
| comunicacion_id | Identificador de la comunicación | Sí          | PK       | Identificador único              | RF-11  |
| evento_id       | Evento relacionado               | Sí          | FK, NN   | Debe existir                     | RF-11  |
| asunto          | Asunto de la comunicación        | Sí          | NN       | No vacío                         | RF-11  |
| contenido       | Contenido del mensaje            | Sí          | NN       | No vacío                         | RF-11  |
| fecha_envio     | Fecha del envío                  | Sí          | NN       | Fecha válida                     | RF-11  |
| estado          | Estado de la comunicación        | Sí          | NN       | Valores definidos por el dominio | RF-11  |

El destinatario concreto queda pendiente de definición.

## 11. CERTIFICADO

| Campo                 | Significado                                   | Obligatorio | PK/FK/UQ | Dominio/regla                    | Origen       |
|-----------------------|-----------------------------------------------|-------------|----------|----------------------------------|--------------|
| certificado_id        | Identificador del certificado                 | Sí          | PK       | Identificador único              | RF-12        |
| participante_id       | Participante beneficiario                     | Sí          | FK, NN   | Debe existir                     | RF-12, RF-20 |
| evento_id             | Evento asociado                               | Sí          | FK, NN   | Debe existir                     | RF-12, RF-20 |
| fecha_emision         | Fecha de emisión/habilitación                 | Sí          | NN       | Fecha válida                     | RF-12        |
| estado                | Estado del certificado                        | Sí          | NN       | Valores definidos por el dominio | RF-12        |
| porcentaje_asistencia | Porcentaje utilizado para validar el criterio | Sí          | NN       | Entre 0 y 100                    | RN-07        |

La unicidad por participante/evento queda pendiente.

## 12. FEEDBACK

| Campo           | Significado                         | Obligatorio | PK/FK/UQ | Dominio/regla                | Origen |
|-----------------|-------------------------------------|-------------|----------|------------------------------|--------|
| feedback_id     | Identificador del feedback          | Sí          | PK       | Identificador único          | RF-13  |
| participante_id | Participante que genera el feedback | Sí          | FK, NN   | Debe existir                 | RF-13  |
| evento_id       | Evento evaluado                     | Sí          | FK, NN   | Debe existir                 | RF-13  |
| valoracion      | Valoración otorgada                 | Sí          | NN       | Dominio de valores pendiente | RF-13  |
| comentario      | Comentario del participante         | No          | —        | Texto opcional               | RF-13  |
| fecha           | Fecha del feedback                  | Sí          | NN       | Fecha válida                 | RF-13  |

La cantidad de feedback permitida por participante/evento queda pendiente.

## 13. Criterios generales de nulabilidad

No todos los atributos se consideran obligatorios automáticamente.

Un atributo puede ser NULL cuando la ausencia representa un estado válido del
dominio.

Los atributos obligatorios se justifican cuando:

- la ficha exige el dato;
- el dato es necesario para identificar o relacionar la instancia;
- el dato es necesario para aplicar una RN;
- la entidad no tiene sentido válido sin ese dato.

## 14. Dominios preliminares

### EVENTO.estado

Valores permitidos:

- BORRADOR
- PUBLICADO
- EN_CURSO
- FINALIZADO
- CANCELADO

### capacidad

Debe ser un valor positivo.

### posicion

Debe ser un valor positivo.

### porcentaje_asistencia

Debe encontrarse entre 0 y 100.

### Fechas y horas

Cuando exista inicio y fin, la finalización no debe preceder al inicio.

Estas condiciones se convertirán en restricciones físicas o validaciones de
backend en etapas posteriores.