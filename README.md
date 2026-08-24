# EventHub

Plataforma para la gestión y promoción de eventos, experiencias y actividades presenciales o híbridas, con un enfoque orientado al entretenimiento.

## 1. Problema

La organización de eventos puede requerir gestionar información sobre actividades, fechas, lugares, sesiones, participantes y asistencia utilizando diferentes medios y procesos.

Esto puede dificultar la publicación de eventos, el control de cupos, la gestión de inscripciones, el seguimiento de participantes y la comunicación de información relevante antes y durante las actividades.

EventHub busca centralizar estos procesos en una plataforma que permita administrar el ciclo de vida de los eventos desde su publicación hasta el registro de asistencia y cierre.

## 2. Objetivo del MVP

Construir una plataforma web y móvil que permita gestionar el ciclo de vida de eventos y actividades, desde su publicación y organización hasta la inscripción de participantes, control de cupos, lista de espera, registro de asistencia, comunicación y cierre.

El equipo utilizará un enfoque orientado al entretenimiento para contextualizar los eventos. Por ejemplo, un evento puede representar un concierto, festival, fiesta temática, convención o experiencia presencial.

Este enfoque corresponde a la forma en que el equipo presenta el dominio y no modifica los requisitos oficiales establecidos para el proyecto PA-10.

## 3. Actores principales

* **Administrador:** configura catálogos, usuarios y parámetros, y supervisa la operación completa.
* **Organizador:** supervisa, aprueba o coordina procesos relacionados con los eventos, con visibilidad ampliada y trazabilidad.
* **Facilitador:** ejecuta el trabajo especializado asignado, actualiza estados y aporta evidencias o seguimiento.
* **Participante:** consulta eventos y realiza acciones autorizadas como solicitar, confirmar, cancelar y participar en actividades.

## 4. Alcance inicial

* Gestión de eventos.
* Gestión de sesiones de eventos.
* Gestión de sedes y salas.
* Gestión de participantes.
* Gestión de inscripciones.
* Control de cupos.
* Gestión de lista de espera.
* Registro y control de asistencia.
* Gestión de comunicaciones relacionadas con eventos.
* Habilitación de certificados académicos simples según el criterio de asistencia.
* Gestión de feedback de participantes.
* Consulta de reportes e indicadores del dominio.
* Autenticación y permisos según el rol vigente.

Los eventos podrán contextualizarse como conciertos, festivales, fiestas temáticas, convenciones, experiencias u otras actividades presenciales o híbridas.

## 5. Fuera de alcance

* Pagos reales.
* Emisión legal de certificados.
* Integración con sistemas bancarios reales.
* Facturación fiscal.
* Control mediante cámaras o reconocimiento facial.
* Sistemas físicos de acceso.
* Geolocalización en tiempo real.
* Inteligencia artificial para determinar precios o autorizar accesos.
* Integración automática con redes sociales.

La función de Spring AI prevista para el proyecto tendrá un uso acotado y secundario, orientado a generar borradores de descripciones o comunicaciones de eventos y resumir feedback. No podrá bloquear el funcionamiento esencial del MVP.

## 6. Flujo principal del sistema

El flujo crítico que debe demostrarse de extremo a extremo es:

**Organizador publica evento → participante se inscribe → el sistema controla cupo/lista de espera → se registra asistencia → se habilita certificado según criterio.**

Este flujo constituye el proceso principal que permitirá comprobar la integración entre los diferentes componentes del sistema.

## 7. Reglas de negocio principales

* Un evento debe tener responsable, modalidad, fechas, capacidad y estado.
* Los estados oficiales del evento son:

    * BORRADOR
    * PUBLICADO
    * EN_CURSO
    * FINALIZADO
    * CANCELADO
* Una inscripción debe respetar el cupo disponible.
* Si el cupo se llena y la lista de espera está habilitada, el participante pasa a la lista de espera.
* No puede existir una inscripción duplicada para el mismo participante y evento.
* La promoción desde la lista de espera debe ser ordenada y trazable.
* La asistencia se registra por sesión o evento según la configuración.
* El certificado académico simple sólo se habilita cuando se cumple el porcentaje mínimo de asistencia.
* Las operaciones que cambian el estado de un proceso de negocio deben registrar fecha y usuario.

## 8. Stack objetivo del semestre

* Backend: Java 21 + Spring Boot
* Base de datos: PostgreSQL + Flyway
* Web: React + TypeScript
* Móvil: React Native + TypeScript
* Pruebas API: Postman
* Contenedores: Docker / Docker Compose
* Versionado: Git + GitHub
* CI: GitHub Actions

## 9. Estado actual

**Clase 01:** comprensión del problema, definición inicial del alcance, identificación de actores, conceptos principales del dominio y creación del backlog inicial.

**Clase 02:** análisis conceptual del dominio, identificación y justificación de entidades, atributos, relaciones, cardinalidades y reglas iniciales de integridad.

Actualmente el proyecto se encuentra en la transición entre el modelo conceptual y el modelo relacional.

Todavía no se ha definido el DDL físico definitivo de PostgreSQL.

## 10. Documentación

* `docs/01-vision/vision-v0.1.md`
* `docs/01-vision/glossary-v0.1.md`
* `docs/02-requirements/backlog-v0.1.md`
* `docs/03-decisions/`
* `docs/04-model/model-conceptual-v0.1.md`
* `docs/04-model/eventhub-der-conceptual-v0.1.png`

## 11. Regla de trabajo

Cada cambio importante debe ser comprensible, trazable y defendible.

Las decisiones del equipo deben poder relacionarse con los requisitos y reglas de negocio oficiales del proyecto.

El repositorio será la fuente de verdad del proyecto y la documentación deberá mantenerse coherente con el modelo y la implementación.

## 12. Fuente oficial

Proyecto: **PA-10 — EventHub: Gestión de Eventos, Inscripciones y Control de Asistencia**.

Fuente: **Banco Oficial de Proyectos Integradores — Programación Aplicada 2026-2**.
