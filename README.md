# AI.BERT - Profile

> Microservicio de autenticación  para A.IBERT — maneja la configuración del perfil académico y la edición de datos personales del estudiante.

---

# Tabla de Contenido

- Descripción General
- Equipo
- Objetivos
- Planteamiento del Problema
- Requerimientos
- Arquitectura
- Stack Tecnológico
- Diagramas
- Gestión del Proyecto
- Pruebas y Calidad
- Demo
- Instalación
- Referencias

---

# Descripción General

## Resumen Ejecutivo

* Problema: Los estudiantes necesitan un lugar centralizado donde configurar su información académica y personal, datos que son consumidos por el motor de planificación para personalizar su experiencia.
  
* Solución: Microservicio independiente que gestiona el perfil académico (carrera, semestre, horas disponibles) y el perfil personal (nombre, foto, contraseña) del estudiante autenticado.

* Usuarios objetivo: Estudiantes universitarios de la ECI.
  

## Alcance

### Incluye
- Configuración del perfil académico (carrera, semestre, horas disponibles)
- Edición del perfil personal (nombre y foto de perfil)
- Cambio de contraseña desde dentro de la aplicación

---

#  Equipo

**Nombre del equipo**

| Integrante | Rol | Responsabilidades |
|-----------|------|------------------|
| Mariana Parra | Lider |  Revisión, coordinación con otros equipos|
| Dana Leal | Backend | UI/UX |
| Andres Sabogal | DevOps | Infraestructura |
| Nicolas Parrado| Arquitectura | Diseño del sistema |

---

# Objetivos

## Objetivo General

Construir un microservicio que centralice la gestión del perfil del estudiante y exponga los datos académicos al motor de planificación para personalizar la distribución de tareas.

## Objetivos Específicos

- Persistir y actualizar el perfil académico del estudiante (carrera, semestre, horas disponibles)
- Permitir la edición de datos personales incluyendo foto de perfil
- Gestionar el cambio de contraseña invalidando todas las sesiones activas del usuario

---

# Planteamiento del Problema

## Contexto

A.IBERT es una plataforma de planificación académica compuesta por microservicios independientes. Para que funcionen de forma coordinada, todos necesitan identificar al usuario que hace cada petición de forma segura y sin acoplar su lógica de autenticación.

## Problema

Sin un perfil académico configurado, el motor de planificación no tiene los parámetros necesarios (semestre, horas disponibles, carrera) para generar un plan de estudio personalizado.

## Dificultades Actuales

- Los datos del perfil están dispersos sin un microservicio que los centralice


## Solución Propuesta

Un microservicio dedicado (profile-service) que persiste y expone los datos del perfil académico y personal del estudiante, con endpoints consumibles por el motor de planificación vía Feign Client.
---

#  Requerimientos

---

## Requerimientos Funcionales

| ID | Requerimiento | Módulo |
|----|---------------|--------|
| R03 | Configuración del perfil académico | Perfil |
| R04 | Edición del perfil personal y cambio de contraseña| Perfil |

---

##  Análisis de Requerimientos

- [Documento de análisis](https://docs.google.com/document/d/1MUhO_lg_SAV1FV18G1dWbMSLK4jfOcMa/edit?usp=sharing&ouid=111980033368663407661&rtpof=true&sd=true)

---

#  Arquitectura

## Arquitectura General

El profile-service forma parte de una arquitectura de microservicios. Recibe peticiones del frontend a través del API Gateway (puerto 8000), que valida el JWT antes de redirigir al servicio.

Frontend (React) → API Gateway :8000 → profile-service :8001 → PostgreSQL.

Patrón: Arquitectura hexagonal (entrypoints → application → domain → infrastructure)

El JWT es validado por el Gateway antes de llegar al servicio



---

#  Stack Tecnológico

| Área | Tecnologías |
|------|-------------|
| Backend | Java 21, Spring Boot |
| Frontend | React / Angular |
| API | REST, OpenAPI |
| Seguridad | Spring Security, JWT |
| SQL | PostgreSQL |
| NoSQL | MongoDB |
| Testing | JUnit, Mockito |
| DevOps | Docker, GitHub Actions |
| Calidad | SonarCloud, JaCoCo |

---

#  Diagramas

## Contexto
- [Diagrama de contexto](docs/diagramas/contexto.png)



## Diagrama de Clases
![DiagramadeClases.png](docs/uml/DiagramadeClases.png)

## Componentes General

![ComponentesGeneral.png](docs/uml/ComponentesGeneral.png)

## Componentes especifico

![ComponentesEspecifico.png](docs/uml/ComponentesEspecifico.png)



---

#  Gestión del Proyecto

## Metodología

- Scrum

## Sprints

| Sprint | Objetivo | Estado |
|-------|----------|--------|
| Sprint 1 | Setup proyecto | ✅ |


---

#  Pruebas

## Estrategia

- Unitarias
- Integración


## Reporte

[Ver reporte pruebas](docs/testing/pruebas.md)

---

# Cobertura

Reporte generado con **JaCoCo** y analizado con **SonarQube**

| Métrica | Cubierto | Total | Cobertura |
|---|---|---|---|
| Líneas | 1964 | 2188 | 90% |
| Ramas | 509 | 745 | 68% |
| Métodos | 744 | 794 | 94% |

## Calidad

- Bugs: 0 críticos
- Code Smells: X
- Deuda técnica: Baja
- Quality Gate: ✅ Passed

---

#  Demo

## Video Demo
- https://youtu.be/yeUNeGnnxpw


---

# Instalación

## Requisitos

- Java 21
- Docker
- PostgreSQL

## Clonar repositorio

```bash
git clone https://github.com/usuario/proyecto.git
cd proyecto
```

## Backend

```bash
./mvnw spring-boot:run
```

## Frontend

```bash
npm install
npm run dev
```

---

# Estructura del Proyecto

```bash
src/
docs/
tests/
docker/
```

---

#  Referencias

- Documentación oficial Spring
- PostgreSQL docs
- OpenAPI
- Papers / fuentes usadas

---

