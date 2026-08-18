# DER lógico v0.1 — EventHub

## 1. Convenciones

- PK = clave primaria.
- FK = clave foránea.
- UQ = unicidad.
- NN = atributo obligatorio.
- NULL = atributo que puede no tener valor.
- Las FK se ubican en el lado N de una relación 1:N.
- Las restricciones se justifican mediante reglas de negocio o requisitos
  funcionales de PA-10.

## 2. Tablas núcleo

### EVENTO

PK evento_id

NN nombre  
NN responsable  
NN modalidad  
NN fecha_inicio  
NN fecha_fin  
NN capacidad  
NN estado

El atributo `descripcion` es opcional.

### SESION_EVENTO

PK sesion_evento_id

FK evento_id -> EVENTO.evento_id

NN fecha  
NN hora_inicio  
NN hora_fin

### SEDE

PK sede_id

NN nombre  
NN direccion

### SALA

PK sala_id

FK sede_id -> SEDE.sede_id

NN nombre  
NN capacidad

### PARTICIPANTE

PK participante_id

NN nombre  
NN apellido  
NN correo

### INSCRIPCION

PK inscripcion_id

FK participante_id -> PARTICIPANTE.participante_id  
FK evento_id -> EVENTO.evento_id

NN fecha_inscripcion  
NN estado

UQ (participante_id, evento_id)

La unicidad compuesta protege RN-04, que impide una inscripción duplicada
para el mismo participante y evento.

### LISTA_ESPERA

PK lista_espera_id

FK participante_id -> PARTICIPANTE.participante_id  
FK evento_id -> EVENTO.evento_id

NN fecha_ingreso  
NN posicion  
NN estado

La combinación `(participante_id, evento_id)` es candidata a UQ, pero queda
pendiente de confirmar debido a los posibles reingresos a la lista de espera.

### ASISTENCIA

FK participante_id -> PARTICIPANTE.participante_id
FK sesion_evento_id -> SESION_EVENTO.sesion_evento_id

NN fecha_registro
NN estado

UQ (participante_id, sesion_evento_id)

La asistencia se registra por sesión. Cada registro de asistencia corresponde
a un participante y a una sesión específica.

## 3. Conceptos adicionales del modelo mínimo

El proyecto PA-10 también requiere analizar:

- COMUNICACION
- CERTIFICADO
- FEEDBACK

Estos conceptos forman parte del modelo de información mínimo oficial y no
deben eliminarse del modelo definitivo.

En esta versión se consideran conceptos de apoyo al núcleo del flujo crítico
y sus atributos y relaciones definitivas serán consolidados después de cerrar
las decisiones pendientes de asistencia y certificados.

### COMUNICACION

PK comunicacion_id

FK evento_id -> EVENTO.evento_id

Atributos conceptuales:

- asunto
- contenido
- fecha_envio
- estado

La relación exacta con participantes queda pendiente de definir según el
alcance de las comunicaciones.

### CERTIFICADO

PK certificado_id

FK participante_id -> PARTICIPANTE.participante_id  
FK evento_id -> EVENTO.evento_id

Atributos conceptuales:

- fecha_emision
- estado
- porcentaje_asistencia

La habilitación depende de RN-07.

### FEEDBACK

PK feedback_id

FK participante_id -> PARTICIPANTE.participante_id  
FK evento_id -> EVENTO.evento_id

Atributos conceptuales:

- valoracion
- comentario
- fecha

La cardinalidad y condición exacta de feedback quedan pendientes de
confirmación.

## 4. Relaciones

### Evento — SesionEvento

EVENTO 1 ---- N SESION_EVENTO

Un evento puede tener cero o varias sesiones.

Cada sesión pertenece a un único evento.

La FK está en SESION_EVENTO.

### Sede — Sala

SEDE 1 ---- N SALA

Una sede puede tener cero o varias salas.

Cada sala pertenece a una única sede.

La FK está en SALA.

### Participante — Inscripcion

PARTICIPANTE 1 ---- N INSCRIPCION

Un participante puede tener cero o varias inscripciones.

Cada inscripción corresponde a un único participante.

### Evento — Inscripcion

EVENTO 1 ---- N INSCRIPCION

Un evento puede tener cero o varias inscripciones.

Cada inscripción corresponde a un único evento.

Estas dos relaciones resuelven:

PARTICIPANTE N:M EVENTO

mediante INSCRIPCION.

### Participante — ListaEspera

PARTICIPANTE 1 ---- N LISTA_ESPERA

Un participante puede aparecer en cero o varias listas de espera.

Cada registro corresponde a un único participante.

### Evento — ListaEspera

EVENTO 1 ---- N LISTA_ESPERA

Un evento puede tener cero o varios registros de lista de espera.

Cada registro corresponde a un único evento.

### Participante — Asistencia

PARTICIPANTE 1 ---- N ASISTENCIA

Un participante puede tener cero o varias asistencias.

Cada asistencia corresponde a un único participante.

La FK `asistencia.participante_id` referencia al participante.

### SesionEvento — Asistencia

SESION_EVENTO 1 ---- N ASISTENCIA

Una sesión puede tener cero o varias asistencias.

Cada asistencia corresponde a una única sesión.

La FK `asistencia.sesion_evento_id` referencia a la sesión correspondiente.

### Evento — Comunicacion

EVENTO 1 ---- N COMUNICACION

Un evento puede tener cero o varias comunicaciones.

Cada comunicación se relaciona con un evento.

### Participante — Certificado

PARTICIPANTE 1 ---- N CERTIFICADO

Un participante puede tener cero o varios certificados.

Cada certificado corresponde a un participante.

### Evento — Certificado

EVENTO 1 ---- N CERTIFICADO

Un evento puede generar cero o varios certificados.

Cada certificado corresponde a un evento.

### Participante — Feedback

PARTICIPANTE 1 ---- N FEEDBACK

Un participante puede generar cero o varios feedback.

Cada feedback corresponde a un participante.

### Evento — Feedback

EVENTO 1 ---- N FEEDBACK

Un evento puede recibir cero o varios feedback.

Cada feedback corresponde a un evento.

## 5. Reglas que afectan el modelo

- RN-01 -> EVENTO requiere responsable, modalidad, fechas, capacidad y estado.
- RN-02 -> EVENTO.estado sólo puede utilizar los estados oficiales.
- RN-03 -> INSCRIPCION debe respetar el cupo y utilizar LISTA_ESPERA cuando
  corresponda.
- RN-04 -> INSCRIPCION debe impedir duplicados por participante/evento.
- RN-05 -> LISTA_ESPERA debe conservar el orden y permitir trazabilidad.
- RN-06 -> ASISTENCIA debe poder representar asistencia por sesión o evento
  según configuración.
- RN-07 -> CERTIFICADO sólo puede habilitarse cuando se cumple el porcentaje
  mínimo de asistencia.
- RN-08 -> no se modelan pagos reales ni emisión legal de certificados.

## 6. Decisiones pendientes

### D-LOG-01 — Registro de asistencia

Se decidió registrar la asistencia por `SesionEvento`.

La tabla `ASISTENCIA` referencia mediante FK a `SESION_EVENTO`.

Esto permite registrar específicamente a qué sesión asistió cada participante.

La relación queda:

PARTICIPANTE 1:N ASISTENCIA N:1 SESION_EVENTO

Además, se establece como candidata a restricción:

`UNIQUE(participante_id, sesion_evento_id)`

para impedir que un participante tenga más de un registro de asistencia para
la misma sesión.

### D-LOG-02 — Reingreso a lista de espera

Debe confirmarse si un participante puede volver a entrar en la lista de
espera del mismo evento después de haber sido promovido o cancelado.

### D-LOG-03 — Comunicaciones

Debe definirse si una comunicación se dirige a todos los participantes de un
evento o si se requiere una relación adicional entre comunicación y
participante.

### D-LOG-04 — Feedback

Debe definirse si un participante puede registrar más de un feedback para el
mismo evento.

### D-LOG-05 — Certificado

Debe confirmarse si existe como máximo un certificado por participante/evento.
Mientras no se cierre esta regla, no se fija una UQ definitiva.

## 7. Trazabilidad

| Elemento      | RN/RF                             |
|---------------|-----------------------------------|
| EVENTO        | RN-01, RN-02, RF-05, RF-15        |
| SESION_EVENTO | RF-06                             |
| SEDE / SALA   | RF-07                             |
| INSCRIPCION   | RN-03, RN-04, RF-08, RF-16, RF-17 |
| LISTA_ESPERA  | RN-03, RN-05, RF-09, RF-16, RF-19 |
| ASISTENCIA    | RN-06, RF-10, RF-18               |
| COMUNICACION  | RF-11                             |
| CERTIFICADO   | RN-07, RF-12, RF-20               |
| FEEDBACK      | RF-13                             |