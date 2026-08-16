# Decisiones técnicas y de producto

Esta carpeta almacenará las decisiones relevantes del proyecto EventHub.

Cada decisión importante deberá indicar:

1. Contexto.
2. Problema o decisión.
3. Alternativas consideradas.
4. Decisión tomada.
5. Consecuencias conocidas.

## Estado inicial

Durante la Clase 01 no se registró una decisión arquitectónica formal, ya que
el proyecto se encontraba en la etapa de comprensión del problema, definición
del alcance y organización inicial del producto.

## Decisiones de Clase 02

### DEC-001 — Enfoque de entretenimiento para EventHub

**Contexto:**
El proyecto oficial PA-10 corresponde a una plataforma para la gestión de
eventos, inscripciones y control de asistencia. El equipo busca contextualizar
el producto principalmente en eventos de entretenimiento y experiencias.

**Problema o decisión:**
Definir cómo presentar el dominio de EventHub sin alejarse de los requisitos
oficiales del proyecto.

**Alternativas consideradas:**

* Mantener el proyecto con un enfoque principalmente académico.
* Enfocarlo en eventos de entretenimiento, manteniendo los requisitos oficiales.
* Crear un sistema centrado en venta de entradas, mesas y productos.

**Decisión tomada:**
El equipo utilizará un enfoque orientado al entretenimiento y las experiencias,
utilizando ejemplos como conciertos, festivales, fiestas temáticas y otros
eventos presenciales o híbridos.

Este enfoque corresponde a la contextualización del dominio y no modifica los
requisitos, reglas de negocio ni conceptos mínimos establecidos oficialmente
para PA-10.

**Consecuencias conocidas:**

* El producto tendrá una identidad más cercana al objetivo del equipo.
* Las entidades y funcionalidades deberán seguir siendo compatibles con la
  ficha oficial de PA-10.
* No se incorporarán funcionalidades como venta de entradas, reservas de
  mesas o revendedores si no están respaldadas por el alcance oficial.

### DEC-002 — Modelo conceptual antes del modelo físico

**Contexto:**
Durante la Clase 02 se debe definir qué conceptos existen en el dominio y cómo
se relacionan antes de crear las tablas físicas.

**Problema o decisión:**
Evitar comenzar directamente con PostgreSQL, DDL o entidades JPA sin haber
cerrado primero el análisis conceptual.

**Alternativas consideradas:**

* Crear directamente las tablas de PostgreSQL.
* Crear primero entidades JPA.
* Construir primero el modelo conceptual y posteriormente transformarlo al
  modelo relacional.

**Decisión tomada:**
El equipo trabajará primero el modelo conceptual de EventHub y posteriormente
lo transformará en un modelo relacional.

**Consecuencias conocidas:**

* Las decisiones sobre PK, FK, tablas puente y normalización se revisarán en
  la siguiente etapa.
* Las dudas de cardinalidad y estructura se documentarán antes de convertirlas
  en restricciones físicas.
