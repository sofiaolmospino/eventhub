# Glosario v0.1 — EventHub

## Propósito

Este glosario define los principales términos utilizados en EventHub para
mantener un lenguaje común entre el equipo, la documentación y la futura
implementación.

## Términos del dominio

| Término           | Definición                                                                                                                                                 |
| ----------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **EventHub**      | Plataforma para gestionar eventos, experiencias y actividades presenciales o híbridas.                                                                     |
| **Evento**        | Actividad organizada que puede representar, dentro del enfoque del equipo, un concierto, festival, fiesta temática, convención u otra experiencia.         |
| **SesionEvento**  | Sesión o bloque específico que forma parte de un evento.                                                                                                   |
| **Sede**          | Lugar físico general donde se realizan eventos.                                                                                                            |
| **Sala**          | Espacio específico ubicado dentro de una sede y destinado a una actividad o sesión.                                                                        |
| **Participante**  | Persona que consulta eventos y puede inscribirse, cancelar su inscripción y participar en las actividades.                                                 |
| **Inscripcion**   | Registro que representa la participación de una persona en un evento.                                                                                      |
| **ListaEspera**   | Registro de participantes que esperan un cupo cuando un evento se encuentra lleno y la lista de espera está habilitada.                                    |
| **Asistencia**    | Registro que indica la participación efectiva de una persona en un evento o sesión.                                                                        |
| **Comunicacion**  | Mensaje relacionado con un evento y dirigido a informar a participantes o responsables.                                                                    |
| **Certificado**   | Registro de un certificado académico simple que puede habilitarse cuando se cumple el porcentaje mínimo de asistencia.                                     |
| **Feedback**      | Retroalimentación proporcionada por un participante sobre un evento o actividad.                                                                           |
| **Administrador** | Actor responsable de configurar usuarios, catálogos y parámetros y supervisar la operación.                                                                |
| **Organizador**   | Actor que supervisa, aprueba o coordina procesos relacionados con los eventos.                                                                             |
| **Facilitador**   | Actor que ejecuta trabajo especializado, actualiza estados y aporta evidencias o seguimiento.                                                              |
| **Estado**        | Situación en la que se encuentra un evento o proceso en un momento determinado.                                                                            |
| **Cupo**          | Capacidad máxima de participantes permitida para un evento.                                                                                                |
| **Promoción**     | Proceso mediante el cual una persona pasa desde la lista de espera a una inscripción cuando existe disponibilidad.                                         |
| **Modalidad**     | Forma en que se desarrolla un evento, de acuerdo con la configuración del dominio.                                                                         |
| **Flujo crítico** | Secuencia principal que EventHub debe demostrar de extremo a extremo: publicación, inscripción, control de cupo/lista de espera, asistencia y certificado. |
| **Reporte**       | Información agregada utilizada para consultar indicadores del dominio.                                                                                     |

## Estados oficiales del evento

Los estados definidos oficialmente para Evento son:

* **BORRADOR**
* **PUBLICADO**
* **EN_CURSO**
* **FINALIZADO**
* **CANCELADO**

## Reglas de uso del vocabulario

* Se utilizará **Evento** y no "fiesta" como nombre de entidad, ya que Evento es el concepto oficial del proyecto.
* "Fiesta", "concierto", "festival" y otros ejemplos se utilizarán como tipos o contextos posibles de eventos, no como entidades independientes.
* **Participante** será utilizado para la persona que interactúa con el sistema en el contexto de inscripción y asistencia.
* **Organizador**, **Administrador** y **Facilitador** se tratarán inicialmente como actores o roles.
* **ListaEspera** y **Asistencia** se consideran conceptos persistentes del dominio.
* Los términos deberán mantenerse consistentes entre requisitos, modelo conceptual, DER, API y código.
