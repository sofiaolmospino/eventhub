# Modelo conceptual v0.1 — EventHub

## 1. Objetivo

Este documento presenta el modelo conceptual inicial de EventHub, una plataforma
orientada a la gestión de eventos, experiencias y actividades presenciales o
híbridas.

El enfoque del equipo busca representar EventHub como una plataforma de
entretenimiento y experiencias, donde pueden gestionarse eventos como
conciertos, festivales, fiestas temáticas, convenciones, encuentros y otras
actividades presenciales o híbridas.

El sistema debe permitir administrar el ciclo de vida de los eventos, desde su
publicación y organización hasta la inscripción de participantes, control de
cupos, lista de espera, registro de asistencia, comunicación y cierre.

El enfoque de entretenimiento corresponde a la forma en que el equipo
contextualiza el dominio. No modifica los requisitos funcionales ni las reglas
de negocio oficiales del proyecto PA-10.

Todavía no se define el modelo físico de PostgreSQL ni las claves foráneas
definitivas.

## 2. Fuente analizada

* Proyecto asignado: EventHub
* Código: PA-10
* Proyecto oficial: EventHub - Gestión de Eventos, Inscripciones y Control de Asistencia
* Fuente: Banco Oficial de Proyectos Integradores — Programación Aplicada 2026-2

### Secciones revisadas

* A. Situación del cliente
* B. Objetivo contractual del producto
* C. Actores y responsabilidades
* D. Alcance funcional cerrado
* E. Reglas de negocio obligatorias
* F. Modelo de información mínimo esperado
* G. Requisitos funcionales predefinidos
* J. Flujo crítico que debe demostrarse extremo a extremo

## 3. Candidatos analizados

| Concepto      | Clasificación      | Justificación                                                                                                                                            | Fuente                        |
| ------------- | ------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| Evento        | Entidad            | Tiene identidad, datos propios, capacidad, fechas, modalidad, responsable y estado. Es el concepto central del flujo del sistema.                        | RN-01, RN-02, RF-05, RF-15, F |
| SesionEvento  | Entidad            | Representa una sesión o bloque perteneciente a un evento y debe conservar información propia.                                                            | RF-06, F                      |
| Sede          | Entidad            | Representa el lugar físico donde pueden realizarse eventos. Tiene identidad y datos propios.                                                             | RF-07, F                      |
| Sala          | Entidad            | Representa un espacio específico dentro de una sede y requiere identidad propia.                                                                         | RF-07, F                      |
| Participante  | Entidad            | Representa a la persona que consulta eventos, se inscribe, cancela y participa en las actividades.                                                       | C, F                          |
| Inscripcion   | Entidad            | Representa la inscripción de un participante a un evento y permite controlar cupo y duplicados.                                                          | RN-03, RN-04, RF-08           |
| ListaEspera   | Entidad            | Permite conservar participantes cuando un evento alcanza su cupo y registrar su posición y promoción.                                                    | RN-03, RN-05, RF-09, RF-19    |
| Asistencia    | Entidad            | Conserva el registro de asistencia de un participante por evento o sesión según la configuración.                                                        | RN-06, RF-10, RF-18           |
| Comunicacion  | Entidad            | Representa comunicaciones relacionadas con los eventos y sus participantes.                                                                              | D, RF-11                      |
| Certificado   | Entidad            | Representa el certificado académico simple habilitado cuando se cumple el porcentaje mínimo de asistencia.                                               | RN-07, RF-12, RF-20           |
| Feedback      | Entidad            | Conserva la retroalimentación proporcionada por participantes después de una actividad.                                                                  | D, RF-13                      |
| Usuario       | Entidad candidata  | La autenticación y los permisos dependen de usuarios y roles. Se analiza como posible entidad adicional para soportar la administración y autenticación. | C, RF-01                      |
| Administrador | Actor/Rol          | Supervisa la operación y configura usuarios, catálogos y parámetros. No se considera entidad independiente automáticamente.                              | C                             |
| Organizador   | Actor/Rol          | Supervisa, aprueba o coordina eventos y procesos específicos.                                                                                            | C                             |
| Facilitador   | Actor/Rol          | Ejecuta actividades especializadas, actualiza estados y registra evidencias o seguimiento.                                                               | C                             |
| Estado        | Estado             | Representa la situación de un evento o proceso y no necesita ser una entidad independiente en esta versión.                                              | RN-02, RN-03, RN-05           |
| Reporte       | Resultado derivado | Representa información agregada para consulta y análisis, por lo que no se considera entidad núcleo persistente en esta versión.                         | D, RF-14                      |

### Criterio utilizado

Para decidir si un concepto debía ser tratado como entidad se consideró si
existe en el dominio, necesita distinguirse de otras instancias, conserva
información propia y participa en relaciones, reglas, estados o históricos.

Los actores no se convierten automáticamente en entidades. En particular,
Administrador, Organizador y Facilitador se mantienen inicialmente como roles
de interacción con el sistema.

## 4. Entidades núcleo v0.1

### Evento

**Responsabilidad:**

Representar una actividad o experiencia organizada dentro de EventHub.

En el contexto del equipo puede representar, por ejemplo, un concierto,
festival, fiesta temática, convención o actividad presencial/híbrida.

**Atributos conceptuales:**

* nombre
* descripción
* responsable
* modalidad
* fecha de inicio
* fecha de fin
* capacidad
* estado

**Identificador de negocio candidato:**

* código o identificador del evento

**Reglas relacionadas:**

* Debe tener responsable, modalidad, fechas, capacidad y estado.
* Sus estados oficiales son BORRADOR, PUBLICADO, EN_CURSO, FINALIZADO y CANCELADO.

### SesionEvento

**Responsabilidad:**

Representar una sesión, actividad o bloque específico que forma parte de un
evento.

Por ejemplo, un festival podría tener diferentes sesiones, presentaciones,
actividades o bloques dentro del evento.

**Atributos conceptuales candidatos:**

* nombre
* fecha
* hora de inicio
* hora de fin
* estado
* configuración de asistencia

**Identificador de negocio candidato:**

* identificador de sesión

### Sede

**Responsabilidad:**

Representar el lugar físico general donde se realizan eventos.

Puede corresponder, por ejemplo, a un centro de eventos, recinto, club,
auditorio, parque o espacio para actividades.

**Atributos conceptuales candidatos:**

* nombre
* dirección
* referencia
* estado

**Identificador de negocio candidato:**

* identificador de sede

### Sala

**Responsabilidad:**

Representar un espacio específico dentro de una sede.

Puede representar un salón, escenario, auditorio, área o espacio destinado a
una actividad concreta.

**Atributos conceptuales candidatos:**

* nombre
* capacidad
* ubicación
* estado

**Identificador de negocio candidato:**

* identificador de sala

### Participante

**Responsabilidad:**

Representar a la persona que utiliza EventHub para consultar, solicitar,
confirmar, cancelar o participar en eventos.

**Atributos conceptuales candidatos:**

* nombre
* apellido
* correo
* teléfono
* estado

**Identificador de negocio candidato:**

* correo o identificador de participante

### Inscripcion

**Responsabilidad:**

Representar la inscripción de un participante en un evento.

Es el concepto que permite controlar la participación, respetar el cupo y
evitar inscripciones duplicadas.

**Atributos conceptuales candidatos:**

* fecha de inscripción
* estado
* fecha de cancelación, cuando corresponda
* fecha de actualización

**Identificador de negocio candidato:**

* identificador de inscripción

### ListaEspera

**Responsabilidad:**

Representar a los participantes que esperan un cupo disponible cuando el
evento está lleno y la lista de espera se encuentra habilitada.

**Atributos conceptuales candidatos:**

* posición
* fecha de ingreso
* estado
* fecha de promoción
* fecha de actualización

**Identificador de negocio candidato:**

* identificador de lista de espera

### Asistencia

**Responsabilidad:**

Registrar la asistencia de un participante a un evento o sesión, dependiendo
de la configuración definida.

**Atributos conceptuales candidatos:**

* fecha y hora de registro
* estado
* método de registro
* evidencia o código utilizado, cuando corresponda

**Identificador de negocio candidato:**

* identificador de asistencia

### Comunicacion

**Responsabilidad:**

Representar mensajes y comunicaciones relacionados con los eventos y sus
participantes.

En el enfoque de entretenimiento puede utilizarse para enviar recordatorios,
cambios de horario, avisos de ubicación, novedades del evento o información
importante para los asistentes.

**Atributos conceptuales candidatos:**

* asunto
* contenido
* fecha de envío
* tipo
* estado

**Identificador de negocio candidato:**

* identificador de comunicación

### Certificado

**Responsabilidad:**

Representar el certificado académico simple que puede habilitarse para un
participante cuando cumple el porcentaje mínimo de asistencia establecido.

Aunque EventHub se contextualice principalmente como una plataforma de
eventos y entretenimiento, este concepto se conserva porque forma parte del
alcance oficial de PA-10.

**Atributos conceptuales candidatos:**

* fecha de habilitación
* estado
* código o identificador del certificado

**Identificador de negocio candidato:**

* identificador de certificado

### Feedback

**Responsabilidad:**

Representar la opinión o retroalimentación proporcionada por un participante
sobre una actividad o evento.

**Atributos conceptuales candidatos:**

* valoración
* comentario
* fecha
* estado

**Identificador de negocio candidato:**

* identificador de feedback

### Usuario — entidad candidata adicional

**Responsabilidad:**

Representar la identidad utilizada para autenticación y control de permisos
dentro de la plataforma.

Esta entidad se mantiene como candidata porque la ficha exige autenticación
y permisos según el rol vigente, pero no aparece dentro de las once entidades
mínimas de la sección F.

**Atributos conceptuales candidatos:**

* nombre
* correo
* credenciales
* rol
* estado

**Decisión pendiente:**

Confirmar durante el diseño relacional si Usuario debe persistirse como
entidad independiente o si su información se integrará con la estructura de
Participante y los roles administrativos.

## 5. Relaciones

### Evento — SesionEvento

Un Evento puede estar compuesto por una o varias sesiones.

Cada SesionEvento pertenece a un único Evento.

### Sede — Sala

Una Sede puede contener varias salas.

Cada Sala pertenece a una Sede.

### Evento — Inscripcion

Un Evento puede recibir inscripciones de múltiples Participantes.

Cada Inscripcion corresponde a un único Evento.

### Participante — Inscripcion

Un Participante puede realizar inscripciones a diferentes Eventos.

Cada Inscripcion corresponde a un único Participante.

### Evento — ListaEspera

Un Evento puede tener participantes en ListaEspera cuando se alcanza el cupo
y la lista se encuentra habilitada.

Cada registro de ListaEspera corresponde a un Evento.

### Participante — ListaEspera

Un Participante puede aparecer en listas de espera de diferentes Eventos.

Cada registro de ListaEspera corresponde a un Participante.

### Participante — Asistencia

Un Participante puede tener registros de asistencia correspondientes a
diferentes eventos o sesiones.

Cada registro de Asistencia corresponde a un Participante.

### Evento/SesionEvento — Asistencia

La Asistencia se registra por Evento o por SesionEvento según la configuración
del evento.

La relación exacta entre Asistencia y SesionEvento queda pendiente de revisión
porque la ficha permite ambas modalidades.

### Evento — Comunicacion

Un Evento puede tener varias Comunicaciones relacionadas con su organización
y desarrollo.

Cada Comunicación se relaciona con el contexto de un Evento.

### Participante — Certificado

Un Participante puede recibir certificados correspondientes a diferentes
Eventos cuando cumple el criterio de asistencia.

### Evento — Certificado

Un Evento puede generar certificados para múltiples Participantes que cumplan
el criterio correspondiente.

### Participante — Feedback

Un Participante puede proporcionar Feedback sobre diferentes Eventos.

Cada Feedback corresponde a un Participante.

### Evento — Feedback

Un Evento puede recibir Feedback de diferentes Participantes.

Cada Feedback corresponde al contexto de un Evento.

## 6. Cardinalidades

Las siguientes cardinalidades representan una propuesta conceptual inicial.
Las reglas oficiales no especifican todos los mínimos y máximos, por lo que
aquellos que no puedan demostrarse directamente desde la ficha quedan como
decisiones revisables.

| Relación                   | Cardinalidad inicial | Justificación                                                                                             |
| -------------------------- | -------------------- | --------------------------------------------------------------------------------------------------------- |
| Evento — SesionEvento      | 1:N                  | Un evento puede organizarse mediante diferentes sesiones y cada sesión pertenece a un evento.             |
| Sede — Sala                | 1:N                  | Una sede puede contener varias salas y cada sala pertenece a una sede.                                    |
| Evento — Inscripcion       | 1:N                  | Un evento puede recibir múltiples inscripciones y cada inscripción corresponde a un evento.               |
| Participante — Inscripcion | 1:N                  | Un participante puede inscribirse en diferentes eventos y cada inscripción corresponde a un participante. |
| Evento — ListaEspera       | 1:N                  | Un evento puede tener múltiples participantes esperando un cupo.                                          |
| Participante — ListaEspera | 1:N                  | Un participante puede estar en listas de espera de diferentes eventos.                                    |
| Participante — Asistencia  | 1:N                  | Un participante puede acumular diferentes registros de asistencia.                                        |
| Evento — Asistencia        | 1:N                  | Un evento puede tener múltiples registros de asistencia.                                                  |
| SesionEvento — Asistencia  | 0..N                 | La asistencia puede registrarse por sesión según la configuración del evento.                             |
| Evento — Comunicacion      | 1:N                  | Un evento puede tener múltiples comunicaciones.                                                           |
| Participante — Certificado | 1:N                  | Un participante puede recibir certificados correspondientes a diferentes eventos.                         |
| Evento — Certificado       | 1:N                  | Un evento puede habilitar certificados para múltiples participantes.                                      |
| Participante — Feedback    | 1:N                  | Un participante puede proporcionar feedback sobre diferentes eventos.                                     |
| Evento — Feedback          | 1:N                  | Un evento puede recibir feedback de diferentes participantes.                                             |

### Cardinalidades pendientes de confirmar

La ficha oficial no define explícitamente:

* si todo Evento debe tener al menos una SesionEvento;
* si una SesionEvento debe tener obligatoriamente una Sala;
* si un Evento puede realizarse sin una Sala;
* si un Participante puede cancelar una Inscripcion y conservarla como histórico;
* si un Participante puede tener más de un registro de Asistencia para una misma
  sesión;
* si un Participante puede generar más de un Feedback para el mismo Evento;
* si un Evento puede tener más de un Certificado para el mismo Participante.

Estas decisiones no se resolverán mediante supuestos ocultos y deberán
validarse antes del modelo relacional.

## 7. Reglas iniciales de integridad

### RI-01 — Datos obligatorios del evento

Todo Evento debe conservar responsable, modalidad, fechas, capacidad y estado.

Fuente: RN-01.

### RI-02 — Estados válidos del evento

El estado de un Evento solo puede ser:

* BORRADOR
* PUBLICADO
* EN_CURSO
* FINALIZADO
* CANCELADO

Fuente: RN-02.

### RI-03 — Control de cupo

Una Inscripcion debe respetar la capacidad disponible del Evento.

Fuente: RN-03.

### RI-04 — Lista de espera

Cuando el cupo de un Evento se encuentra lleno y la lista de espera está
habilitada, el participante debe pasar a ListaEspera.

Fuente: RN-03.

### RI-05 — No duplicación de inscripciones

No puede existir más de una Inscripcion para la misma combinación
Participante-Evento.

Fuente: RN-04.

### RI-06 — Promoción ordenada

La promoción de participantes desde ListaEspera debe conservar el orden y
permitir identificar cómo y cuándo se produjo la promoción.

Fuente: RN-05.

### RI-07 — Asistencia según configuración

La Asistencia debe registrarse por Evento o por SesionEvento según la
configuración establecida.

Fuente: RN-06.

### RI-08 — Certificado condicionado

Un Certificado solo puede habilitarse cuando el participante cumple el
porcentaje mínimo de asistencia establecido.

Fuente: RN-07.

### RI-09 — Trazabilidad

Las operaciones que cambien el estado de un proceso de negocio deben registrar
fecha y usuario.

Fuente: RF-04.

### RI-10 — Alcance de pagos y certificados

EventHub no implementará pagos reales ni emisión legal de certificados.

Fuente: RN-08.

## 8. Dudas y decisiones

### Decisiones

**D-01. Enfoque del dominio**

El equipo utilizará un enfoque de entretenimiento y experiencias para
contextualizar los eventos y ejemplos del sistema, manteniendo las reglas y
módulos exigidos por PA-10.

**D-02. Actores y entidades**

Administrador, Organizador y Facilitador se consideran inicialmente actores o
roles y no entidades independientes.

Participante sí se considera entidad porque posee información propia y
participa directamente en inscripciones, lista de espera, asistencia,
certificados y feedback.

**D-03. Estado**

Los estados se consideran condiciones del ciclo de vida de las entidades y no
entidades independientes.

**D-04. ListaEspera**

ListaEspera se considera entidad porque necesita conservar posición, estado y
trazabilidad de las promociones.

**D-05. Asistencia**

Asistencia se considera entidad porque representa información que debe
persistir y puede ser necesaria para determinar la habilitación del certificado.

**D-06. Certificado**

Certificado se mantiene como entidad porque representa un resultado persistente
que depende del cumplimiento de un criterio de asistencia.

### Dudas pendientes

**D-07. Usuario**

Determinar si Usuario debe ser una entidad independiente para soportar
autenticación, roles y trazabilidad.

**D-08. Responsable del evento**

Determinar cómo se representa la relación entre Evento y su responsable,
considerando los roles definidos por la ficha.

**D-09. Sesiones y salas**

Confirmar si toda SesionEvento debe estar asociada obligatoriamente a una Sala.

**D-10. Asistencia**

Determinar la estructura conceptual para soportar asistencia por Evento o por
SesionEvento sin duplicar información.

**D-11. Cancelaciones**

Determinar si una inscripción cancelada permanece como registro histórico.

**D-12. Comunicación**

Determinar si una Comunicación se relaciona únicamente con un Evento o si
también requiere una relación directa con los Participantes destinatarios.

**D-13. Feedback**

Determinar si existe una única valoración por Participante-Evento o si puede
haber múltiples registros.

## 9. Trazabilidad inicial

| Concepto / relación               | RN / RF asociado                  |
| --------------------------------- | --------------------------------- |
| Evento                            | RN-01, RN-02, RF-05, RF-15        |
| SesionEvento                      | RF-06                             |
| Sede                              | RF-07                             |
| Sala                              | RF-07                             |
| Participante                      | C, F, RF-08                       |
| Inscripcion                       | RN-03, RN-04, RF-08, RF-16, RF-17 |
| ListaEspera                       | RN-03, RN-05, RF-09, RF-16, RF-19 |
| Asistencia                        | RN-06, RF-10, RF-18               |
| Comunicacion                      | RF-11                             |
| Certificado                       | RN-07, RF-12, RF-20               |
| Feedback                          | RF-13                             |
| Reportes                          | RF-14                             |
| Autenticación y roles             | RF-01                             |
| Validación de datos obligatorios  | RF-03                             |
| Trazabilidad de cambios de estado | RF-04                             |

## 10. Prueba del flujo crítico

El modelo debe permitir recorrer el flujo crítico oficial de EventHub:

### Paso 1 — Organizador publica evento

**Entidades involucradas:**

* Evento
* Usuario/rol responsable, si se confirma como entidad

**Relaciones y reglas:**

* Responsable del Evento
* RN-01
* RN-02
* RF-05
* RF-15

El Evento debe contar con la información obligatoria y encontrarse en un estado
válido para ser publicado.

### Paso 2 — Participante se inscribe

**Entidades involucradas:**

* Participante
* Evento
* Inscripcion

**Relaciones y reglas:**

* Participante — Inscripcion
* Evento — Inscripcion
* RN-03
* RN-04
* RF-08
* RF-17

El sistema debe comprobar el cupo y evitar que el mismo participante tenga
inscripciones duplicadas para el mismo evento.

### Paso 3 — El sistema controla cupo y lista de espera

**Entidades involucradas:**

* Evento
* Inscripcion
* Participante
* ListaEspera

**Relaciones y reglas:**

* RN-03
* RN-05
* RF-16
* RF-19

Cuando el cupo está lleno y la lista de espera está habilitada, el participante
debe quedar registrado en la lista de espera.

### Paso 4 — Se registra asistencia

**Entidades involucradas:**

* Participante
* Evento
* SesionEvento
* Asistencia

**Relaciones y reglas:**

* RN-06
* RF-10
* RF-18

La asistencia se registra a nivel de evento o sesión según la configuración.

### Paso 5 — Se habilita el certificado

**Entidades involucradas:**

* Participante
* Asistencia
* Evento
* Certificado

**Relaciones y reglas:**

* RN-07
* RF-12
* RF-20

El certificado solo se habilita cuando el participante cumple el porcentaje
mínimo de asistencia.
