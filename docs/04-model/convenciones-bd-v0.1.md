# Convenciones de Base de Datos — EventHub v0.1

## 1. Propósito

Este documento define las convenciones físicas iniciales que utilizará
EventHub para preparar la implementación posterior en PostgreSQL.

Las convenciones se derivan del modelo lógico, del diccionario de datos y
de las reglas y requisitos oficiales de PA-10.

Esta versión todavía no contiene DDL ejecutable.

## 2. Nombres

### Tablas

Las tablas utilizarán nombres en:

- minúsculas
- snake_case
- singular

Ejemplos:

- evento
- sesion_evento
- participante
- inscripcion
- lista_espera

### Columnas

Las columnas utilizarán:

- minúsculas
- snake_case
- nombres descriptivos del dominio

Ejemplos:

- evento_id
- fecha_inicio
- hora_inicio
- porcentaje_asistencia

### Claves primarias

Las PK utilizarán el patrón:

`<tabla>_id`

Ejemplos:

- evento_id
- participante_id
- inscripcion_id

### Claves foráneas

Las FK conservarán el nombre del identificador de la entidad referenciada.

Ejemplo:

`evento_id` en `inscripcion` referencia `evento.evento_id`.

### Restricciones UNIQUE

Las restricciones UNIQUE utilizarán nombres descriptivos cuando se
implementen físicamente.

Ejemplo conceptual:

`uq_inscripcion_participante_evento`

### Restricciones CHECK

Las restricciones CHECK utilizarán nombres que indiquen la condición.

Ejemplo conceptual:

`ck_evento_capacidad_positiva`

### Índices

Los índices seguirán el patrón:

`idx_<tabla>_<columna>`

Se crearán solamente cuando exista una necesidad de consulta o una
justificación de rendimiento.

No se crearán índices decorativos.

## 3. Estrategia de identificadores

### Decisión

Se utilizará `BIGINT` autogenerado como identificador técnico candidato.

### Justificación

La ficha no exige UUID ni una estrategia distribuida de identificadores.

`BIGINT` proporciona un identificador técnico estable y suficiente para el
alcance del proyecto.

La PK técnica no reemplaza las claves naturales o datos de negocio que
necesiten unicidad.

## 4. Tipos candidatos

| Tipo de dato       | Uso candidato                                     |
|--------------------|---------------------------------------------------|
| BIGINT             | Identificadores técnicos                          |
| VARCHAR(n)         | Textos cortos y controlados                       |
| TEXT               | Descripciones y contenido de longitud variable    |
| INTEGER            | Cantidades y capacidades                          |
| NUMERIC(p,s)       | Importes o valores decimales exactos, si aparecen |
| DATE               | Fechas donde sólo importa el día calendario       |
| TIMESTAMPTZ        | Instantes con fecha y hora                        |
| BOOLEAN            | Banderas verdaderas/falsas                        |
| VARCHAR(n) + CHECK | Estados con conjunto cerrado de valores           |

## 5. Fechas y horas

Se utilizará `TIMESTAMPTZ` cuando importe el instante de una operación.

Ejemplos:

- fecha_inscripcion
- fecha_ingreso
- fecha_registro
- fecha_emision
- created_at
- updated_at

Cuando sólo importe el día calendario se podrá utilizar `DATE`.

Las fechas y horas de sesiones se consideran instantes porque una sesión
tiene inicio y finalización.

## 6. Estados

Los estados se almacenarán como valores legibles.

Para `evento.estado` se consideran:

- BORRADOR
- PUBLICADO
- EN_CURSO
- FINALIZADO
- CANCELADO

La RN-02 establece este conjunto de estados.

No se utilizarán valores numéricos opacos como:

- 1
- 2
- 3
- 4

sin un contrato explícito.

## 7. Nulabilidad

No se utilizará NOT NULL automáticamente en todos los atributos.

Cada columna se analizará según la pregunta:

> ¿Puede existir una instancia válida sin conocer este dato en ese momento?

Si la respuesta es no, será obligatoria.

Si la respuesta es sí, podrá ser nullable.

## 8. Dinero y precisión

Si una futura versión incorpora importes monetarios, se utilizará
`NUMERIC(p,s)` y no FLOAT/DOUBLE como elección por defecto.

En el alcance oficial actual no se implementan pagos reales.

Esto se deriva de RN-08.

## 9. Auditoría

Las entidades mutables relevantes evaluarán:

- created_at
- updated_at

RF-04 también exige registrar fecha y usuario en las operaciones que cambian
el estado de un proceso.

La identidad de usuario y la estructura definitiva de auditoría todavía deben
cerrarse antes de convertir esa trazabilidad en FK física.

Por esta razón, la auditoría completa se registra como decisión pendiente y
no se inventa una relación que todavía no existe en el modelo.

## 10. Regla de equipo

Toda excepción a estas convenciones debe:

1. Tener una razón relacionada con el dominio.
2. Estar documentada.
3. Poder justificarse mediante una RN, RF o decisión técnica.

## 11. Criterio

Las convenciones buscan que el esquema sea:

- consistente;
- legible;
- trazable;
- preparado para PostgreSQL;
- coherente con el modelo lógico.