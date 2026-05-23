<div align="center">

# 👤 grootology-profile-service

### *Módulo 1 — Gestión de Perfiles — A.IBERT ECI Planner*

> Centraliza el ciclo de vida del usuario: registro, verificación por OTP, gestión
> de perfil personal y académico, administración y eliminación de cuentas.

---

### 🛠️ Stack Tecnológico

![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-1.5.5-red?style=for-the-badge)
![Lombok](https://img.shields.io/badge/Lombok-1.18.32-pink?style=for-the-badge)

### ☁️ Infraestructura & Calidad

![Azure](https://img.shields.io/badge/Azure-Container%20Apps-0078D4?style=for-the-badge&logo=microsoft-azure&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Container-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-80%25%20min-D1322B?style=for-the-badge)
![SonarCloud](https://img.shields.io/badge/SonarCloud-Quality-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white)

### 🏗️ Arquitectura

![Hexagonal](https://img.shields.io/badge/Architecture-Hexagonal-blueviolet?style=for-the-badge)
![REST API](https://img.shields.io/badge/REST-API-009688?style=for-the-badge)
![Microservices](https://img.shields.io/badge/Microservices-Pattern-FF6B35?style=for-the-badge)

</div>

---

## 📑 Tabla de Contenidos

1. [👤 Integrantes](#1--integrantes)
2. [🎯 Descripción del Módulo](#2--descripción-del-módulo)
3. [⚙️ Tecnologías Utilizadas](#3--tecnologías-utilizadas)
4. [🏗️ Cómo Funciona el Módulo](#4--cómo-funciona-el-módulo)
   - [4.1 Módulos con los que se comunica](#41-módulos-con-los-que-se-comunica)
   - [4.2 Patrones utilizados](#42-patrones-utilizados)
   - [4.3 Estilo de arquitectura detallado](#43-estilo-de-arquitectura-detallado)
   - [4.4 Reglas de negocio críticas](#44-reglas-de-negocio-críticas)
5. [📊 Diagramas](#5--diagramas)
   - [5.1 Diagrama de Clases](#51-diagrama-de-clases)
   - [5.2 Diagrama de Componentes](#52-diagrama-de-componentes)
   - [5.3 Diagrama de Secuencia — Registro](#53-diagrama-de-secuencia--registro)
   - [5.4 Diagrama de Secuencia — Actualización de Perfil Personal](#54-diagrama-de-secuencia--actualización-de-perfil-personal)
   - [5.5 Diagrama de Secuencia — Actualización de Perfil Académico](#55-diagrama-de-secuencia--actualización-de-perfil-académico)
6. [⚡ Funcionalidades](#6--funcionalidades)
   - [6.1 R01 — Registro de Usuario](#61-r01--registro-de-usuario)
   - [6.2 R02 — Verificación de Email (OTP)](#62-r02--verificación-de-email-otp)
   - [6.3 R03 — Reenvío de Verificación](#63-r03--reenvío-de-verificación)
   - [6.4 R04 — Actualización de Perfil Personal](#64-r04--actualización-de-perfil-personal)
   - [6.5 R05 — Actualización de Foto de Perfil](#65-r05--actualización-de-foto-de-perfil)
   - [6.6 R06 — Perfil Académico](#66-r06--perfil-académico)
   - [6.7 R07 — Cambio de Contraseña](#67-r07--cambio-de-contraseña)
   - [6.8 R08 — Eliminación de Cuenta](#68-r08--eliminación-de-cuenta)
   - [6.9 R09 — Administración de Usuarios (Admin)](#69-r09--administración-de-usuarios-admin)
7. [🔌 Conexiones con Servicios Externos](#7--conexiones-con-servicios-externos)
8. [⚠️ Manejo de Errores](#8--manejo-de-errores)
9. [📋 Estrategia de Versionamiento y Branches](#9--estrategia-de-versionamiento-y-branches)
   - [9.1 Convenciones para crear ramas](#91-convenciones-para-crear-ramas)
   - [9.2 Convenciones para crear commits](#92-convenciones-para-crear-commits)
10. [🧪 Evidencia de Pruebas Unitarias](#10--evidencia-de-pruebas-unitarias)
11. [📈 Evidencia de Análisis de Cobertura](#11--evidencia-de-análisis-de-cobertura)
12. [🗂️ Código Organizado por Carpetas](#12--código-organizado-por-carpetas)
13. [🚀 Cómo Ejecutar el Proyecto](#13--cómo-ejecutar-el-proyecto)
14. [☁️ CI/CD y Despliegue en Azure](#14--cicd-y-despliegue-en-azure)
    - [14.1 Jobs del Pipeline](#141-jobs-del-pipeline)
    - [14.2 Evidencia del Despliegue](#142-evidencia-del-despliegue)
    - [14.3 Link Swagger en Azure](#143-link-swagger-en-azure)
15. [🔐 Variables de Entorno](#15--variables-de-entorno)
16. [📚 Referencias](#16--referencias)

---

## 1. 👤 Integrantes

**Módulo 1 — Gestión de Perfiles**  
**Proyecto:** A.IBERT — ECI Planner  
**Institución:** Escuela Colombiana de Ingeniería Julio Garavito

<div align="center">

| 👨‍💻 Integrante | 🎓 Rol | 📋 Responsabilidades |
|Nicolás Parrado|Ingeniero dev-ops||
|Mariana Parra|Ingeniero Frontend||
|Jeyder Leon|Ingeniero Backend||
|Andres Sabogal|Documentación y diagramación||
|Dana Leal|Marketing||
|Juan Hernández|Ingeniero backend||

</div>

---

## 2. 🎯 Descripción del Módulo

El **Módulo 1 — Gestión de Perfiles** es el guardián de la identidad del usuario dentro de A.IBERT — ECI Planner.

Mientras que el `auth-service` se encarga de emitir tokens JWT, este servicio almacena y gestiona todo lo que define al usuario: sus datos personales, su carrera, sus metas académicas y su disponibilidad. Es la fuente de verdad sobre quién es cada usuario en la plataforma.

<div align="center">

| ✅ **Qué hace** | ❌ **Problema que resuelve** |
|:---|:---|
| Registra usuarios y verifica la cuenta mediante OTP | Sin control de quién puede entrar a la plataforma |
| Centraliza perfil personal y académico del estudiante | Datos del usuario dispersos entre múltiples servicios |
| Provee endpoints internos para que `auth-service` consulte datos | Acoplamiento directo entre servicios al consultar usuarios |
| Registra logs de auditoría de cambios administrativos | Sin trazabilidad de quién modificó qué y cuándo |
| Permite a admins gestionar, desactivar y cambiar roles | Sin panel de control centralizado para la plataforma |
| Bloquea el OTP tras 3 intentos fallidos (15 min) | Ataques de fuerza bruta sobre el código de verificación |

</div>

### Microservicios del Módulo 1

| Microservicio | Puerto | Responsabilidad |
|---|---|---|
| profile-service | 1501 (local) / 8082 (Docker) | Gestión completa del perfil y ciclo de vida del usuario |

---

## 3. ⚙️ Tecnologías Utilizadas

<div align="center">

| **Tecnología / Herramienta** | **Uso principal en el proyecto** |
|---|---|
| **Java 21** | Lenguaje de programación base del microservicio backend. |
| **Spring Boot 3.4.3** | Framework principal para construir el microservicio, exponiendo APIs REST. |
| **Spring Web** | Exposición de endpoints REST dentro de la arquitectura hexagonal. |
| **Spring Security + jjwt 0.11.5** | Filtro JWT (`JwtAuthFilter`) que valida el token en cada request protegido. |
| **Spring Data JPA** | Integración con la base de datos relacional usando el patrón Repository. |
| **PostgreSQL 16** | Base de datos relacional para persistir usuarios, tokens y logs de auditoría. |
| **Apache Maven** | Gestión de dependencias, empaquetado y automatización de builds en CI/CD. |
| **MapStruct 1.5.5.Final** | Mapeo automático entre capas sin código manual. |
| **Lombok 1.18.32** | Reducción de código repetitivo con `@Builder`, `@Getter`, `@RequiredArgsConstructor`. |
| **Spring Mail (SMTP)** | Envío de correos de verificación OTP por email institucional. |
| **SpringDoc OpenAPI 2.8.5** | Swagger UI automático en `/swagger-ui.html`. |
| **Spring Boot Validation** | Bean Validation con `@Valid`, `@NotBlank`, `@Email`, `@Pattern`, etc. |
| **JUnit 5** | Framework de pruebas unitarias para validar casos de uso y dominio. |
| **Mockito** | Simulación de dependencias (puertos, repositorios) en pruebas unitarias. |
| **H2** | Base de datos en memoria para el perfil de pruebas en CI. |
| **JaCoCo 0.8.x** | Generación de reportes de cobertura (mínimo 80% INSTRUCTION). |
| **SonarCloud** | Análisis estático del código e identificación de vulnerabilidades. |
| **Docker** | Contenedorización del microservicio (multistage build). |
| **Azure Container Apps** | Entorno de ejecución en la nube donde se despliega el contenedor. |
| **GHCR** | Registro de imágenes Docker (`ghcr.io/ai-bert-backend/`). |
| **GitHub Actions** | Pipelines de integración y despliegue continuo (CI/CD). |

</div>

> 🧠 **Stack seleccionado** para garantizar **escalabilidad**, **modularidad**, **mantenibilidad** y **separación estricta de capas**, aplicando arquitectura hexagonal.

---

## 4. 🏗️ Cómo Funciona el Módulo

### 4.1 Módulos con los que se comunica

El `profile-service` es consumido por el `auth-service` para obtener y actualizar datos de autenticación, y provee datos de perfil académico a otros módulos:

```
profile-service
 ├── auth-service  ← Consume endpoints /internal/users/* para login y actualización de intentos
 └── [otros módulos] ← Consumen datos académicos del usuario para personalizar la experiencia
```

<div align="center">

| 🌍 **Microservicio** | ⚙️ **Operación** | 📋 **Propósito** |
|:---|:---|:---|
| **auth-service** | `GET /internal/users/email/{email}` | Obtener datos del usuario para autenticar |
| **auth-service** | `GET /internal/users/{id}` | Obtener datos del usuario por ID |
| **auth-service** | `PUT /internal/users/{id}/auth` | Actualizar intentos fallidos y bloqueo temporal |
| **API Gateway (:8000)** | HTTP REST | Enruta peticiones externas al servicio |

</div>

> ℹ️ Los endpoints `/internal/users/*` no están protegidos por JWT ya que son de consumo exclusivo entre microservicios internos.

### 4.2 Patrones utilizados

<div align="center">

| 🎨 **Patrón** | 📋 **Descripción** |
|:---|:---|
| **Ports & Adapters (Hexagonal)** | Separación total entre lógica de negocio e infraestructura |
| **Repository Pattern** | Abstracción del acceso a datos con JPA mediante puertos `out` |
| **Use Case Pattern** | Cada operación de negocio tiene su propio caso de uso e interfaz |
| **DTO Pattern** | Separación entre objetos de transferencia y entidades de dominio |
| **MapStruct Mapper** | Conversión automática entre capas sin código manual |
| **Global Exception Handler** | `@RestControllerAdvice` centraliza todas las respuestas de error |
| **Audit Log** | Registro de cambios administrativos via `AuditLogPort` |

</div>

### 4.3 Estilo de arquitectura detallado

El microservicio implementa **Arquitectura Hexagonal (Ports & Adapters)**:

```
┌─────────────────────────────────────────────────────┐
│                    ENTRYPOINTS                      │
│  (AuthController / ProfileController /              │
│   AdminController / InternalUserController /        │
│   GlobalExceptionHandler)                           │
└────────────────────────┬────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────┐
│                   APPLICATION                       │
│  (RegisterService / UpdateProfileService /          │
│   AcademicProfileService / AdminUserService /       │
│   DeleteAccountService / FileUploadService)         │
└────────────────────────┬────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────┐
│                     DOMAIN                          │
│  (User / EmailVerificationToken / Career /          │
│   Role / UserStatus / AcademicGoal / Availability / │
│   Ports / Exceptions)                               │
└────────────────────────┬────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────┐
│                 INFRASTRUCTURE                      │
│  (JPA Entities / Adapters / SMTP / AuditLog)        │
└─────────────────────────────────────────────────────┘
```

**Flujo de dependencias:** `Entrypoints → Application → Domain ← Infrastructure`

> La capa de **Domain** no depende de ninguna otra capa; es el núcleo independiente de la aplicación.

### 4.4 Reglas de negocio críticas

| Área | Regla |
|---|---|
| **Registro** | `email`: debe tener formato `@mail.escuelaing.edu.co`. `fullName`: solo letras y espacios, 3–100 caracteres. |
| **Contraseña** | Mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número. |
| **OTP de verificación** | Código de 6 dígitos. Expira en **5 minutos**. Máximo **3 intentos fallidos** → bloqueo de **15 minutos**. |
| **Email único** | No se pueden registrar dos usuarios con el mismo correo → `400`. |
| **Perfil académico** | `currentSemester`: 1–10. `currentGpa`: 0.0–5.0. `dailyStudyHours`: 1–12. `weeklyHours`: 1–80. Al menos 1 materia. |
| **Cambio de contraseña** | Requiere la contraseña actual para confirmar. Invalida todas las sesiones activas al cambiar. |
| **Eliminación de cuenta** | Requiere contraseña actual como confirmación. Acción irreversible. |
| **Administración** | Solo usuarios con rol `ROLE_ADMIN` pueden acceder a `/api/admin/*`. Los cambios quedan registrados en `AuditLog`. |

---

## 5. 📊 Diagramas

### 5.1 Diagrama de Clases

![Diagrama_de_Clases.png](docs/uml/Diagrama_de_Clases.png)

El diagrama de clases sigue la arquitectura hexagonal dividida en cuatro capas: **Entrypoints**, **Aplicación**, **Dominio** e **Infraestructura**. El dominio contiene las entidades `User`, `EmailVerificationToken` y los enums `Career`, `Role`, `UserStatus`, `AcademicGoal`, `Availability`, más las interfaces de 5 puertos `in` y 4 puertos `out`. La capa de infraestructura implementa esos puertos con adaptadores JPA sin que el dominio lo sepa.

---

### 5.2 Diagrama de Componentes

![Diagrama_de_componentes.png](docs/uml/Diagrama_de_componentes.png)

El diagrama muestra la organización interna del `profile-service`. Cada flujo sigue el patrón en capas: `Controller → UseCase/Service → RepositoryAdapter`, con `MapStruct Mappers` encargados de la conversión entre capas. El `SmtpEmailService` implementa el puerto `EmailServicePort` para el envío de correos OTP.

---

### 5.3 Diagrama de Secuencia — Registro

![Diagrama_secuencia_register.png](docs/uml/Diagrama_secuencia_register.png)

El flujo completo del registro: desde que llegan los datos hasta que se persiste el usuario y se envía el OTP al correo institucional. Incluye la validación de email único y la encriptación BCrypt de la contraseña.

---

### 5.4 Diagrama de Secuencia — Actualización de Perfil Personal

![Diagrama_secuencia_updateProfile.png](docs/uml/Diagrama_secuencia_updateProfile.png)

El flujo de actualización de datos personales (username, foto de perfil), validando el JWT del usuario autenticado antes de persistir los cambios.

---

### 5.5 Diagrama de Secuencia — Actualización de Perfil Académico

![Diagrama_secuencia_saveAcademicProfile.png](docs/uml/Diagrama_secuencia_saveAcademicProfile.png)

El flujo de guardado o actualización del perfil académico, incluyendo la validación de los campos académicos y la marcación del `profileComplete` en `true` al completarse por primera vez.

---

## 6. ⚡ Funcionalidades

> Los endpoints públicos (registro y verificación) están bajo `/api/auth`. Los endpoints protegidos por JWT están bajo `/api/profile` y `/api/admin/users`.

---

### 6.1 R01 — Registro de Usuario

Crea una nueva cuenta de usuario validando el correo institucional, la fortaleza de la contraseña y que el email no esté duplicado. Envía un OTP de 6 dígitos al correo para la verificación posterior.

**Endpoint:** `POST /api/auth/register`

---

#### 📦 Información de Entrada (Request)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricciones | 📝 Descripción |
|---|---|:---:|---|
| `fullName` | `String` | Obligatorio. Solo letras y espacios. 3–100 caracteres. | Nombre completo del usuario. |
| `email` | `String` | Obligatorio. Formato `@mail.escuelaing.edu.co`. | Correo institucional. |
| `career` | `Career` (Enum) | Obligatorio. | Carrera que cursa el usuario. |
| `password` | `String` | Obligatorio. Mínimo 8 caracteres, 1 mayúscula, 1 minúscula, 1 número. | Contraseña de acceso. |
| `confirmPassword` | `String` | Obligatorio. Debe coincidir con `password`. | Confirmación de contraseña. |

</div>

---

#### 📦 Información de Salida (Response)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | 📝 Descripción |
|---|---|---|
| `id` | `UUID` | ID del usuario recién creado. |
| `role` | `String` | Rol asignado al registrarse (`ESTUDIANTE`). |
| `message` | `String` | Confirmación e instrucción para verificar el correo. |

</div>

---

#### ✅ Happy Path (Ejemplo de Uso Exitoso)

**Request:**
```http
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "Nicolas Parrado",
  "email": "n.parrado@mail.escuelaing.edu.co",
  "career": "SOFTWARE_ENGINEERING",
  "password": "MyPassword1",
  "confirmPassword": "MyPassword1"
}
```

**Response `201 Created`:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "role": "ESTUDIANTE",
  "message": "Registro exitoso. Revisa tu correo para verificar tu cuenta."
}
```

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Validación fallida (email, nombre, contraseña) | `"email: Debe ser un correo institucional @mail.escuelaing.edu.co"` |
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Las contraseñas no coinciden | `"Las contraseñas no coinciden"` |
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Email ya registrado | `"El correo ya está registrado"` |

</div>

---

### 6.2 R02 — Verificación de Email (OTP)

Valida el código OTP de 6 dígitos enviado al correo institucional y activa la cuenta. Maneja bloqueo por intentos fallidos y expiración del token.

**Endpoint:** `POST /api/auth/verify-otp`

---

#### 📦 Información de Entrada (Request)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricciones | 📝 Descripción |
|---|---|:---:|---|
| `userId` | `UUID` | Obligatorio (Query param). | ID del usuario a verificar. |
| `otp` | `String` | Obligatorio (Query param). Código de 6 dígitos. | Código OTP recibido por correo. |

</div>

---

#### 📦 Información de Salida (Response)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | 📝 Descripción |
|---|---|---|
| `verificationStatus` | `Boolean` | `true` si el OTP fue válido. |
| `accountStatus` | `Boolean` | `true` si la cuenta quedó activa. |
| `expirationTime` | `Long` | Segundos restantes del OTP (o del bloqueo si aplica). |
| `resendAvailability` | `Boolean` | `true` si se puede solicitar un nuevo OTP. |

</div>

---

#### ✅ Happy Path (Ejemplo de Uso Exitoso)

**Request:**
```http
POST /api/auth/verify-otp?userId=550e8400-e29b-41d4-a716-446655440000&otp=482910
```

**Response `200 OK` — Verificación exitosa:**
```json
{
  "verificationStatus": true,
  "accountStatus": true,
  "expirationTime": 0,
  "resendAvailability": false
}
```

**Response `200 OK` — OTP incorrecto (aún hay intentos):**
```json
{
  "verificationStatus": false,
  "accountStatus": false,
  "expirationTime": 247,
  "resendAvailability": true
}
```

**Response `200 OK` — Cuenta bloqueada por intentos fallidos:**
```json
{
  "verificationStatus": false,
  "accountStatus": false,
  "expirationTime": 893,
  "resendAvailability": false
}
```

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Token inválido o expirado | `"Token inválido o expirado"` |
| ![404](https://img.shields.io/badge/404-Not_Found-orange?style=flat) | Usuario no encontrado | `"Usuario no encontrado"` |

</div>

---

### 6.3 R03 — Reenvío de Verificación

Invalida el OTP anterior y genera uno nuevo, enviándolo al correo institucional del usuario.

**Endpoint:** `POST /api/auth/resend-verification`

---

#### 📦 Información de Entrada (Request)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricciones | 📝 Descripción |
|---|---|:---:|---|
| `email` | `String` | Obligatorio (Query param). | Correo del usuario que solicita el reenvío. |

</div>

---

#### ✅ Happy Path (Ejemplo de Uso Exitoso)

**Request:**
```http
POST /api/auth/resend-verification?email=n.parrado@mail.escuelaing.edu.co
```

**Response `200 OK`:**
```
Código OTP reenviado al correo institucional.
```

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![404](https://img.shields.io/badge/404-Not_Found-orange?style=flat) | Correo no registrado | `"Usuario no encontrado"` |

</div>

---

### 6.4 R04 — Actualización de Perfil Personal

Actualiza el `userName` del usuario autenticado. El nombre de usuario debe cumplir restricciones de formato.

**Endpoint:** `PUT /api/profile/{userId}`

---

#### 📦 Información de Entrada (Request)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricciones | 📝 Descripción |
|---|---|:---:|---|
| `userId` | `UUID` | Obligatorio (Path). | ID del usuario a actualizar. |
| `Authorization` | `String` | Obligatorio (Header). `Bearer <token>`. | JWT del usuario autenticado. |
| `userName` | `String` | 3–30 caracteres. Solo letras, números, puntos y guiones bajos. | Nuevo nombre de usuario. |

</div>

---

#### ✅ Happy Path (Ejemplo de Uso Exitoso)

**Request:**
```http
PUT /api/profile/550e8400-e29b-41d4-a716-446655440000
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{ "userName": "nicolas.parrado" }
```

**Response `200 OK`:**
```json
{ "message": "Perfil actualizado exitosamente." }
```

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Formato de username inválido | `"userName: Solo letras, números, puntos y guiones bajos"` |
| ![401](https://img.shields.io/badge/401-Unauthorized-orange?style=flat) | JWT ausente o inválido | `"Unauthorized"` |
| ![404](https://img.shields.io/badge/404-Not_Found-orange?style=flat) | Usuario no encontrado | `"Usuario no encontrado"` |

</div>

---

### 6.5 R05 — Actualización de Foto de Perfil

Sube y reemplaza la foto de perfil del usuario. La petición debe enviarse como `multipart/form-data`.

**Endpoint:** `PUT /api/profile/{userId}/photo`

---

#### 📦 Información de Entrada (Request)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricciones | 📝 Descripción |
|---|---|:---:|---|
| `userId` | `UUID` | Obligatorio (Path). | ID del usuario. |
| `Authorization` | `String` | Obligatorio (Header). | JWT del usuario autenticado. |
| `photo` | `MultipartFile` | Obligatorio (Form-data). Archivo de imagen. | Nueva foto de perfil. |

</div>

---

#### ✅ Happy Path (Ejemplo de Uso Exitoso)

**Request:**
```http
PUT /api/profile/550e8400-e29b-41d4-a716-446655440000/photo
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: multipart/form-data

photo: [archivo de imagen]
```

**Response `200 OK`:**
```json
{ "message": "Foto actualizada exitosamente." }
```

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Archivo faltante o inválido | `"Datos inválidos"` |
| ![401](https://img.shields.io/badge/401-Unauthorized-orange?style=flat) | JWT ausente o inválido | `"Unauthorized"` |
| ![404](https://img.shields.io/badge/404-Not_Found-orange?style=flat) | Usuario no encontrado | `"Usuario no encontrado"` |

</div>

---

### 6.6 R06 — Perfil Académico

Guarda o actualiza la información académica del usuario. Marca `profileComplete = true` al completarse por primera vez. Esta información es consumida por otros módulos de la plataforma.

**Endpoint:** `PUT /api/profile/{userId}/academic`

---

#### 📦 Información de Entrada (Request)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricciones | 📝 Descripción |
|---|---|:---:|---|
| `userId` | `UUID` | Obligatorio (Path). | ID del usuario. |
| `career` | `Career` (Enum) | Obligatorio. | Carrera principal. |
| `doubleDegreeCareer` | `Career` (Enum) | Opcional. | Segunda carrera si aplica. |
| `currentSemester` | `Integer` | Obligatorio. 1–10. | Semestre actual. |
| `currentGpa` | `Double` | Obligatorio. 0.0–5.0. | Promedio acumulado actual. |
| `currentSubjects` | `List<String>` | Obligatorio. Mínimo 1 elemento. | Materias que cursa actualmente. |
| `academicGoal` | `AcademicGoal` (Enum) | Obligatorio. | Meta académica principal. |
| `currentlyWorking` | `Boolean` | Obligatorio. | Si trabaja actualmente. |
| `availability` | `Availability` (Enum) | Obligatorio. | Disponibilidad para estudiar. |
| `dailyStudyHours` | `Integer` | Obligatorio. 1–12. | Horas diarias de estudio. |
| `weeklyHours` | `Integer` | Obligatorio. 1–80. | Horas semanales disponibles. |

</div>

---

#### 📦 Información de Salida (Response)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | 📝 Descripción |
|---|---|---|
| `career` | `String` | Carrera principal guardada. |
| `doubleDegreeCareer` | `String` | Segunda carrera (si aplica). |
| `currentSemester` | `Integer` | Semestre guardado. |
| `currentGpa` | `Double` | Promedio guardado. |
| `currentSubjects` | `List<String>` | Materias guardadas. |
| `academicGoal` | `AcademicGoal` | Meta guardada. |
| `currentlyWorking` | `Boolean` | Estado laboral guardado. |
| `availability` | `Availability` | Disponibilidad guardada. |
| `weeklyHours` | `Integer` | Horas semanales guardadas. |
| `dailyStudyHours` | `Integer` | Horas diarias guardadas. |
| `profileComplete` | `Boolean` | `true` si el perfil está completo. |

</div>

---

#### ✅ Happy Path (Ejemplo de Uso Exitoso)

**Request:**
```http
PUT /api/profile/550e8400-e29b-41d4-a716-446655440000/academic
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "career": "SOFTWARE_ENGINEERING",
  "currentSemester": 5,
  "currentGpa": 3.8,
  "currentSubjects": ["Algoritmos", "Bases de Datos"],
  "academicGoal": "GRADUATE_WITH_HONORS",
  "currentlyWorking": false,
  "availability": "EVENINGS",
  "dailyStudyHours": 3,
  "weeklyHours": 15
}
```

**Response `200 OK`:**
```json
{
  "career": "SOFTWARE_ENGINEERING",
  "doubleDegreeCareer": null,
  "currentSemester": 5,
  "currentGpa": 3.8,
  "currentSubjects": ["Algoritmos", "Bases de Datos"],
  "academicGoal": "GRADUATE_WITH_HONORS",
  "currentlyWorking": false,
  "availability": "EVENINGS",
  "weeklyHours": 15,
  "dailyStudyHours": 3,
  "profileComplete": true
}
```

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Campos inválidos o faltantes | `"currentSemester: must be between 1 and 10"` |
| ![401](https://img.shields.io/badge/401-Unauthorized-orange?style=flat) | JWT ausente o inválido | `"Unauthorized"` |
| ![404](https://img.shields.io/badge/404-Not_Found-orange?style=flat) | Usuario no encontrado | `"Usuario no encontrado"` |

</div>

---

### 6.7 R07 — Cambio de Contraseña

Cambia la contraseña del usuario autenticado. Requiere la contraseña actual como verificación e invalida todas las sesiones activas tras el cambio.

**Endpoint:** `PUT /api/profile/{userId}/password`

---

#### 📦 Información de Entrada (Request)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricciones | 📝 Descripción |
|---|---|:---:|---|
| `userId` | `UUID` | Obligatorio (Path). | ID del usuario. |
| `currentPassword` | `String` | Obligatorio. | Contraseña actual para verificación. |
| `newPassword` | `String` | Obligatorio. Mínimo 8 caracteres, 1 mayúscula, 1 minúscula, 1 número. | Nueva contraseña. |
| `confirmPassword` | `String` | Obligatorio. Debe coincidir con `newPassword`. | Confirmación de nueva contraseña. |

</div>

---

#### ✅ Happy Path (Ejemplo de Uso Exitoso)

**Request:**
```http
PUT /api/profile/550e8400-e29b-41d4-a716-446655440000/password
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "currentPassword": "MiContraseña1",
  "newPassword": "NuevaContraseña2",
  "confirmPassword": "NuevaContraseña2"
}
```

**Response `200 OK`:**
```json
{ "message": "Contraseña actualizada. Todas las sesiones han sido cerradas." }
```

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Las contraseñas no coinciden | `"Las contraseñas no coinciden"` |
| ![401](https://img.shields.io/badge/401-Unauthorized-orange?style=flat) | Contraseña actual incorrecta | `"Contraseña incorrecta"` |
| ![404](https://img.shields.io/badge/404-Not_Found-orange?style=flat) | Usuario no encontrado | `"Usuario no encontrado"` |

</div>

---

### 6.8 R08 — Eliminación de Cuenta

Elimina permanentemente la cuenta del usuario. Requiere la contraseña actual como confirmación. La acción es **irreversible** e invalida todas las sesiones activas.

**Endpoint:** `DELETE /api/profile/{userId}`

---

#### 📦 Información de Entrada (Request)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricciones | 📝 Descripción |
|---|---|:---:|---|
| `userId` | `UUID` | Obligatorio (Path). | ID del usuario a eliminar. |
| `currentPassword` | `String` | Obligatorio (Query param). | Contraseña actual como confirmación. |

</div>

---

#### ✅ Happy Path (Ejemplo de Uso Exitoso)

**Request:**
```http
DELETE /api/profile/550e8400-e29b-41d4-a716-446655440000?currentPassword=MiContraseña1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Response `200 OK`:**
```json
{
  "accountDeleted": true,
  "sessionInvalidated": true,
  "redirectLogin": true
}
```

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![401](https://img.shields.io/badge/401-Unauthorized-orange?style=flat) | Contraseña incorrecta | `"Contraseña incorrecta"` |
| ![404](https://img.shields.io/badge/404-Not_Found-orange?style=flat) | Usuario no encontrado | `"Usuario no encontrado"` |

</div>

---

### 6.9 R09 — Administración de Usuarios (Admin)

Panel de gestión para administradores. Permite listar, ver detalles, editar, cambiar el estado, eliminar usuarios y modificar roles. Requiere rol `ROLE_ADMIN`.

**Endpoints:**

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/admin/users` | Listar todos los usuarios (con filtros opcionales) |
| `GET` | `/api/admin/users/{userId}` | Ver detalle de un usuario |
| `PUT` | `/api/admin/users/{userId}` | Editar datos de un usuario |
| `PUT` | `/api/admin/users/{userId}/status` | Cambiar estado (`ACTIVE`, `INACTIVE`, `LOCKED`) |
| `PUT` | `/api/admin/users/{userId}/role` | Cambiar rol (`ROLE_USER`, `ROLE_ADMIN`) |
| `DELETE` | `/api/admin/users/{userId}` | Eliminar usuario permanentemente |

---

#### 📦 Filtros disponibles — Listar usuarios

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | 📝 Descripción |
|---|---|---|
| `name` | `String` | Filtro parcial por nombre. |
| `email` | `String` | Filtro parcial por email. |
| `status` | `String` | Filtro por estado: `ACTIVE`, `INACTIVE`, `LOCKED`. |
| `role` | `String` | Filtro por rol: `ROLE_USER`, `ROLE_ADMIN`. |

</div>

---

#### 📊 Tipos de Errores Manejados

<div align="center">

| 🔢 **Código HTTP** | ⚠️ **Escenario** | 💬 **Mensaje de Error** |
|:---:|:---|:---|
| ![400](https://img.shields.io/badge/400-Bad_Request-red?style=flat) | Valor de estado o rol inválido | `"Datos inválidos"` |
| ![401](https://img.shields.io/badge/401-Unauthorized-orange?style=flat) | JWT ausente o inválido | `"Unauthorized"` |
| ![403](https://img.shields.io/badge/403-Forbidden-orange?style=flat) | Usuario sin rol ADMIN | `"Access Denied"` |
| ![404](https://img.shields.io/badge/404-Not_Found-orange?style=flat) | Usuario no encontrado | `"Usuario no encontrado"` |

</div>

---

## 7. 🔌 Conexiones con Servicios Externos

<div align="center">

| 🌍 **Servicio Externo** | 🔗 **Tipo de Conexión** | ⚙️ **Operación** | 📋 **Propósito** | 🗓️ **Estado** |
|:---|:---|:---|:---|:---:|
| **auth-service** | HTTP REST (consumidor) | `GET/PUT /internal/users/*` | Obtener y actualizar datos de autenticación del usuario | Activo |
| **SMTP (email)** | `SmtpEmailService` vía `EmailServicePort` | Envío de correo | Enviar OTP de verificación al registrarse | Activo |
| **API Gateway (:8000)** | HTTP REST | Enruta peticiones externas | Punto de entrada único al ecosistema A.IBERT | Activo |

</div>

---

## 8. ⚠️ Manejo de Errores

El microservicio implementa un **mecanismo centralizado de manejo de errores** mediante `@RestControllerAdvice` en `GlobalExceptionHandler`, garantizando respuestas uniformes y seguras en toda la API.

### Estructura de Error Estandarizada

```json
{
  "error": "El correo ya está registrado"
}
```

### Catálogo de Excepciones de Dominio

<div align="center">

| 🔴 **Excepción** | 🔢 **HTTP** | 📋 **Cuándo ocurre** |
|:---|:---:|:---|
| `MethodArgumentNotValidException` | `400` | Bean Validation falla en el request body |
| `EmailAlreadyRegisteredException` | `400` | Se intenta registrar un email ya existente |
| `InvalidTokenException` | `400` | Token OTP inválido o expirado |
| `IllegalArgumentException` | `400` | Contraseñas no coinciden u otro argumento inválido |
| `InvalidPasswordException` | `401` | Contraseña actual incorrecta al cambiar o eliminar cuenta |
| `UserNotFoundException` | `404` | Usuario con el ID o email dado no existe |

</div>

### Beneficios del Manejo Centralizado

<div align="center">

| 🎯 **Beneficio** | 📋 **Descripción** |
|:---|:---|
| **Uniformidad** | Todas las respuestas de error tienen el mismo formato `{ "error": "..." }` |
| **Mantenibilidad** | Agregar nuevas excepciones no requiere modificar cada controlador |
| **Seguridad** | Oculta detalles internos del servidor en el error genérico `500` |
| **Trazabilidad** | Cada error incluye código HTTP y mensaje descriptivo |

</div>

---

## 9. 📋 Estrategia de Versionamiento y Branches

El equipo utiliza **GitFlow** como modelo de ramificación para el control de versiones.

### Ramas y propósito

#### `main`
- **Propósito:** Rama estable con la versión final lista para producción.
- **Reglas:** Solo recibe merges desde `develop`. Cada merge activa el deploy automático a PROD. Rama **protegida**: PR obligatorio con aprobaciones y CI en verde.

#### `develop`
- **Propósito:** Integración continua; base de nuevas funcionalidades.
- **Reglas:** Recibe merges desde `feature/*`, `fix/*` y `docs/*`. Activa deploy automático a QA.

#### `feature/*`
- **Propósito:** Desarrollo de una funcionalidad específica.
- **Base:** `develop`. **Cierre:** Merge a `develop` mediante PR.

#### `fix/*`
- **Propósito:** Corrección de bugs encontrados en `develop`.
- **Base:** `develop`. **Cierre:** Merge a `develop`.

#### `docs/*`
- **Propósito:** Actualizaciones de documentación y README.
- **Base:** `develop`. **Cierre:** Merge a `develop`.

### Historial de ramas implementadas

| Sprint | Rama | Objetivo | Estado |
|---|---|---|---|
| Sprint 1 | `feature/profile-ci-cd` | Bootstrap base, CI/CD, configuración del pipeline | ✅ Completado |
| Sprint 1 | `feature/dockerizacion` | Dockerización del servicio y docker-compose | ✅ Completado |
| Sprint 1 | `feature/nuevos-requerimientos` | Registro, verificación OTP, perfil personal y académico, admin | ✅ Completado |
| Sprint 2 | `feature/inter-service-integration` | Integración completa con auth-service y demás módulos | 🔜 Pendiente |

---

### 9.1 Convenciones para crear ramas

**Formato:**
```
[tipo]/[nombre-funcionalidad]
```

**Tipos:**
- `feature/` — Nueva funcionalidad
- `fix/` — Corrección de bugs
- `docs/` — Cambios de documentación
- `hotfix/` — Corrección crítica en producción

**Ejemplos:**
```
feature/register-use-case
feature/academic-profile
fix/otp-expiry-validation
docs/readme-update
```

**Reglas:**
- Usar **kebab-case**
- Máximo 50 caracteres
- Nombre descriptivo de la funcionalidad

---

### 9.2 Convenciones para crear commits

**Formato:**
```
[tipo]: [descripción específica de la acción]
```

**Tipos de commit:**
- `feat`: Nueva funcionalidad
- `fix`: Corrección de errores
- `docs`: Cambios en documentación
- `test`: Adición o corrección de pruebas
- `refactor`: Refactorización de código
- `ci`: Cambios en pipeline CI/CD
- `chore`: Tareas de mantenimiento

**Ejemplos:**
```
feat: implementar registro con verificación OTP
feat: agregar gestión de perfil académico
fix: corregir bloqueo de OTP tras 3 intentos fallidos
test: agregar pruebas unitarias para RegisterService
ci: configurar deploy a Azure Container Apps QA
docs: actualizar README con endpoints y arquitectura
```

---

## 10. 🧪 Evidencia de Pruebas Unitarias

El microservicio implementa una **estrategia integral de pruebas** con JUnit 5 y Mockito, organizada por capas de la arquitectura hexagonal.

### Tipos de pruebas implementadas

<div align="center">

| 🧪 **Tipo de Prueba** | 📋 **Descripción** | 🛠️ **Herramientas** |
|:---|:---|:---|
| **Pruebas Unitarias de Services** | Validan la lógica de registro, perfil, admin y eliminación de forma aislada | JUnit 5 + Mockito |
| **Pruebas de Controllers** | Verifican el comportamiento HTTP de cada endpoint | `@WebMvcTest` + MockMvc |
| **Pruebas de DTOs** | Verifican las validaciones de Bean Validation en los requests | JUnit 5 |
| **Pruebas de Configuración** | Validan el filtro JWT y la configuración de seguridad | JUnit 5 + Mockito |
| **Pruebas de Adaptadores** | Validan los adaptadores JPA y los mappers de persistencia | JUnit 5 + Mockito |

</div>

### Cómo ejecutar las pruebas

#### 1️⃣ Ejecutar todas las pruebas

```bash
./mvnw test
```

#### 2️⃣ Ejecutar una prueba específica

```bash
./mvnw test -Dtest=RegisterServiceTest
```

#### 3️⃣ Generar reporte de cobertura con JaCoCo

```bash
./mvnw test jacoco:report
```

El reporte HTML se genera en:
```
target/site/jacoco/index.html
```

#### 4️⃣ Ejecutar pruebas desde IntelliJ IDEA

1. Click derecho sobre la carpeta `src/test/java`
2. Selecciona **"Run 'Tests in...'"**
3. Ver resultados en el panel de pruebas

---

### Ejemplo de prueba unitaria

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("RegisterService Tests")
class RegisterServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private TokenRepositoryPort tokenRepository;

    @Mock
    private EmailServicePort emailService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterService registerService;

    @Test
    @DisplayName("Should register user successfully with valid data")
    void shouldRegisterUserSuccessfully() {
        // Given
        RegisterRequestDTO request = buildValidRequest();
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(buildSavedUser());

        // When
        RegisterResponseDTO result = registerService.register(request);

        // Then
        assertNotNull(result);
        assertEquals("ESTUDIANTE", result.getRole());
        verify(emailService, times(1)).sendVerificationEmail(any(), any());
    }
}
```

---

### Criterios de aceptación de pruebas

- ✅ **Cobertura mínima del 80%** en instrucciones (JaCoCo INSTRUCTION — RNF de calidad)
- ✅ **Todas las pruebas en estado PASSED** antes de merge a `develop`
- ✅ **Cero errores de compilación** en el código de pruebas
- ✅ **Estructura Given/When/Then** y `@DisplayName` descriptivo en cada test
- ✅ **No se exige cobertura** en: DTOs, `config/`, mappers de persistencia, entidades JPA, main class

---

## 11. 📈 Evidencia de Análisis de Cobertura

El análisis de cobertura se realiza con **JaCoCo** y se integra con **SonarCloud** para análisis estático de calidad en cada ejecución del pipeline CI/CD.

### Configuración de JaCoCo

- **Cobertura mínima:** 80% INSTRUCTION (falla el build si no se alcanza)
- **Reporte:** generado automáticamente durante `./mvnw test`
- **Artifact en CI:** subido como `jacoco-report-profile-run{N}-{SHA}` y disponible por 7 días

### Exclusiones de cobertura (JaCoCo + SonarCloud)

```
**/config/**
**/ProfileServiceApplication.java
**/dto/request/**
**/dto/response/**
**/infrastructure/adapters/persistence/entity/**
**/infrastructure/adapters/persistence/mapper/**
**/infrastructure/external/**
```

### Análisis SonarCloud

- **Organización:** `ai-bert-backend`
- **Project Key:** `grootology-profile-service`
- **URL:** [sonarcloud.io](https://sonarcloud.io/project/overview?id=grootology-profile-service)

<div align="center">

| 📊 **Métrica** | 🎯 **Objetivo** |
|:---|:---|
| Cobertura de instrucciones | ≥ 80% |
| Code Smells Blocker | 0 |
| Vulnerabilidades Critical | 0 |
| Imports wildcard (`*`) | 0 — todos explícitos |

</div>

---

## 12. 🗂️ Código Organizado por Carpetas

El microservicio sigue estrictamente la **Arquitectura Hexagonal (Ports & Adapters)**:

```
grootology-profile-service/
│
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/aibert/dosw/
│   │   │   │
│   │   │   ├── 📁 domain/                           # 🟢 CAPA DE DOMINIO
│   │   │   │   ├── 📁 model/user/                   # User, EmailVerificationToken, Role, UserStatus,
│   │   │   │   │                                    # Career, AcademicGoal, Availability
│   │   │   │   ├── 📁 ports/
│   │   │   │   │   ├── 📁 in/                       # RegisterUseCase, UpdateProfileUseCase,
│   │   │   │   │   │                                # AcademicProfileUseCase, AdminUserUseCase,
│   │   │   │   │   │                                # DeleteAccountUseCase
│   │   │   │   │   └── 📁 out/                      # UserRepositoryPort, TokenRepositoryPort,
│   │   │   │   │                                    # EmailServicePort, AuditLogPort
│   │   │   │   └── 📁 exceptions/                   # UserNotFoundException, EmailAlreadyRegisteredException,
│   │   │   │                                        # InvalidPasswordException, InvalidTokenException
│   │   │   │
│   │   │   ├── 📁 application/                      # 🔵 CAPA DE APLICACIÓN
│   │   │   │   ├── 📁 service/                      # RegisterService, UpdateProfileService,
│   │   │   │   │                                    # AcademicProfileService, AdminUserService,
│   │   │   │   │                                    # DeleteAccountService, FileUploadService
│   │   │   │   └── 📁 dto/
│   │   │   │       ├── 📁 request/                  # RegisterRequestDTO, UpdateProfileDTO,
│   │   │   │       │                                # AcademicProfileDTO, PasswordChangeDTO,
│   │   │   │       │                                # AdminEditUserRequestDTO, ChangeRoleRequestDTO,
│   │   │   │       │                                # UserAuthUpdateDTO
│   │   │   │       └── 📁 response/                 # RegisterResponseDTO, AcademicProfileResponseDTO,
│   │   │   │                                        # OtpVerificationResponseDTO, UserSummaryDTO, UserAuthDTO
│   │   │   │
│   │   │   ├── 📁 entrypoints/                      # 🟡 CAPA DE ENTRADA
│   │   │   │   ├── 📁 rest/controller/              # AuthController, ProfileController,
│   │   │   │   │                                    # AdminController, InternalUserController
│   │   │   │   └── 📁 advice/                       # GlobalExceptionHandler
│   │   │   │
│   │   │   ├── 📁 infrastructure/                   # 🟠 CAPA DE INFRAESTRUCTURA
│   │   │   │   ├── 📁 adapters/
│   │   │   │   │   ├── 📁 adapter/                  # UserRepositoryAdapter, TokenRepositoryAdapter,
│   │   │   │   │   │                                # AuditLogAdapter
│   │   │   │   │   └── 📁 persistence/
│   │   │   │   │       ├── 📁 entity/               # UserEntity, EmailVerificationTokenEntity,
│   │   │   │   │       │                            # AuditLogEntity
│   │   │   │   │       ├── 📁 mapper/               # UserPersistenceMapper, TokenPersistenceMapper
│   │   │   │   │       └── 📁 repository/           # UserJpaRepository, TokenJpaRepository,
│   │   │   │   │                                    # AuditLogJpaRepository
│   │   │   │   └── 📁 external/email/               # SmtpEmailService
│   │   │   │
│   │   │   └── 📁 config/
│   │   │       ├── JwtAuthFilter.java               # Filtro JWT — valida el Bearer token en cada request
│   │   │       ├── SecurityConfig.java              # Spring Security — define rutas públicas y protegidas
│   │   │       └── SwaggerConfig.java               # Configuración de SpringDoc OpenAPI
│   │   │
│   │   └── 📁 resources/
│   │       └── application.yml                      # Perfiles: base, local, qa, prod
│   │
│   └── 📁 test/                                     # 🧪 PRUEBAS
│       └── 📁 java/com/aibert/dosw/
│           ├── 📁 application/service/              # RegisterServiceTest, UpdateProfileServiceTest,
│           │                                        # AcademicProfileServiceTest, AdminUserServiceTest,
│           │                                        # DeleteAccountServiceTest, FileUploadServiceTest
│           ├── 📁 application/dto/                  # DtoConstructionTest
│           ├── 📁 config/                           # JwtAuthFilterTest, ConfigAndEmailTest
│           ├── 📁 entrypoints/
│           │   ├── 📁 rest/controller/              # AuthControllerTest, ProfileControllerTest,
│           │   │                                    # AdminControllerTest
│           │   └── 📁 advice/                       # GlobalExceptionHandlerTest
│           └── 📁 infrastructure/adapters/
│               ├── 📁 adapter/                      # UserRepositoryAdapterTest,
│               │                                    # AuditAndTokenAdapterTest
│               └── 📁 persistence/                  # PersistenceEntityTest
│
├── 📁 docs/
│   └── 📁 uml/                                      # Diagramas
│       ├── Diagrama_de_Clases.png
│       ├── Diagrama_de_componentes.png
│       ├── Diagrama_secuencia_register.png
│       ├── Diagrama_secuencia_updateProfile.png
│       └── Diagrama_secuencia_saveAcademicProfile.png
│
├── 📄 Dockerfile                                    # Multistage build — JDK builder + JRE runtime
├── 📄 docker-compose.yml                            # PostgreSQL 16 (5434) + profile-service (8082)
├── 📄 pom.xml
└── 📄 README.md
```

### Arquitectura Hexagonal Implementada

<div align="center">

| 🎨 **Capa** | 📋 **Responsabilidad** | 🔗 **Dependencias** |
|:---|:---|:---|
| **🟢 Domain** | Lógica de negocio pura, entidades, puertos (interfaces) y excepciones | ❌ Ninguna — completamente independiente |
| **🔵 Application** | Implementación de casos de uso, servicios y DTOs | ✅ Solo `Domain` |
| **🟡 Entrypoints** | Controllers REST, InternalUserController y GlobalExceptionHandler | ✅ `Application` + `Domain` |
| **🟠 Infrastructure** | Entidades JPA, adaptadores de repositorio, mappers y SMTP | ✅ `Domain` + `Application` |

</div>

---

## 13. 🚀 Cómo Ejecutar el Proyecto

### 📋 Prerrequisitos

- **Docker** y **Docker Compose** (opción recomendada)
- **Java 21** y **Maven 3.8+** (ejecución local sin Docker)
- Credenciales SMTP para el envío de correos

---

### 🐳 Opción 1: Docker Compose (recomendada)

```bash
# 1. Clonar el repositorio
git clone https://github.com/ai-bert-backend/grootology-profile-service.git
cd grootology-profile-service

# 2. Crear archivo .env con las variables requeridas
cp .env.example .env
# Editar .env con tus valores

# 3. Levantar PostgreSQL + backend
docker compose up --build

# 4. En segundo plano
docker compose up --build -d

# 5. Detener los contenedores
docker compose down
```

| Servicio | Puerto host | Descripción |
|---|---|---|
| `profile-db` | `5434` | PostgreSQL 16 |
| `profile-service` | `8082` | Backend Spring Boot |

📚 **Swagger UI (Docker):** `http://localhost:8082/swagger-ui/index.html`

---

### ☕ Opción 2: Ejecución Local (Maven)

Requiere PostgreSQL corriendo en `localhost:5432` con las credenciales del archivo `.env`.

```bash
# Ejecutar con perfil local (PostgreSQL local)
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# Ejecutar con perfil de test (H2 en memoria)
./mvnw spring-boot:run -Dspring.profiles.active=test
```

📍 **URL Local:** `http://localhost:1501`  
📚 **Swagger UI:** `http://localhost:1501/swagger-ui/index.html`

---

### 🐳 Opción 3: Solo Docker

```bash
# Construir la imagen
docker build -t profile-service .

# Ejecutar el contenedor
docker run -p 1501:8081 \
  -e SPRING_PROFILES_ACTIVE=qa \
  -e DB_URL=jdbc:postgresql://host:5432/profile_db \
  -e DB_USER=profile_user \
  -e DB_PASSWORD=profile_pass \
  -e JWT_SECRET=mi_secreto_jwt \
  -e MAIL_HOST=smtp.example.com \
  -e MAIL_USERNAME=usuario@ejemplo.com \
  -e MAIL_PASSWORD=mi_password_smtp \
  profile-service
```

---

## 14. ☁️ CI/CD y Despliegue en Azure

El proyecto implementa un **pipeline automatizado** con **GitHub Actions** con 5 jobs secuenciales que garantizan la calidad del código y el despliegue continuo en **Azure Container Apps**.

**Trigger:** Push o Pull Request a `develop` o `main`. También manual (`workflow_dispatch`).

---

### 14.1 Jobs del Pipeline

```
compile → test → analyze → build → deploy-qa / deploy-prod
```

```yaml
# .github/workflows/ci_profile-service.yml

jobs:
  compile:     # ./mvnw compile
  test:        # ./mvnw test + publicar resultados + subir target/
  analyze:     # jacoco:report + sonar:sonar + subir artifact JaCoCo
  build:       # docker build + push a GHCR (solo push a develop/main)
  deploy-qa:   # Azure Container Apps QA (solo push a develop)
  deploy-prod: # Azure Container Apps PROD (solo push a main)
```

<div align="center">

| ⚙️ **Job** | 🖥️ **Runner** | 📋 **Descripción** |
|:---|:---:|:---|
| `compile` | ubuntu-latest | Verifica que el código compila con `./mvnw compile` |
| `test` | ubuntu-latest | Ejecuta todos los tests con H2 en memoria, publica resultados XML y sube `target/` |
| `analyze` | ubuntu-latest | Genera reporte JaCoCo + análisis SonarCloud + artifact de 7 días |
| `build` | ubuntu-latest | Build multistage Docker + push a `ghcr.io/ai-bert-backend/` |
| `deploy-qa` | ubuntu-latest | Deploy a Azure Container Apps QA (rama `develop`) |
| `deploy-prod` | ubuntu-latest | Deploy a Azure Container Apps PROD (rama `main`) |

</div>

### Tags de imagen Docker

| Branch | Tags generados |
|---|---|
| `develop` | `qa-latest` + SHA corto |
| `main` | `prod-latest` + SHA corto |

### Secrets requeridos

| Secret | Descripción |
|---|---|
| `SONAR_TOKEN` | Token de SonarCloud |
| `GITHUB_TOKEN` | Automático de GitHub Actions |
| `GHCR_TOKEN` | Token para pull de imagen desde GHCR en Azure |
| `AZURE_CREDENCIALES_QA` | Credenciales Azure para entorno QA |
| `AZURE_CREDENCIALES_PROD` | Credenciales Azure para entorno PROD |
| `DB_URL` | URL de la base de datos PostgreSQL |
| `DB_USER` | Usuario de la base de datos |
| `DB_PASSWORD` | Contraseña de la base de datos |
| `JWT_SECRET` | Clave secreta para validar los tokens JWT |
| `APP_BASE_URL` | URL base del servicio para los enlaces de email |
| `MAIL_HOST` | Host del servidor SMTP |
| `MAIL_PORT` | Puerto del servidor SMTP |
| `MAIL_USERNAME` | Usuario del servidor SMTP |
| `MAIL_PASSWORD` | Contraseña del servidor SMTP |

---

### 14.2 Evidencia del Despliegue

### Infraestructura Azure

<div align="center">

| Componente | Servicio Azure | Valor |
|:---|:---|:---|
| **Compute** | Azure Container Apps | `aibert-profile-service-qa` / `aibert-profile-service-prod` |
| **Resource Group** | Azure Resource Group | `aibert-rg` |
| **Registry** | GitHub Container Registry (GHCR) | `ghcr.io/ai-bert-backend/grootology-profile-service` |
| **Database** | PostgreSQL (Azure) | Inyectada vía `DB_URL` |

</div>

---

### 14.3 Link Swagger en Azure

<div align="center">

| 🌍 Ambiente | 🔗 URL Swagger | 📝 Estado |
|:---|:---|:---:|
| **🟠 QA** | *(agregar URL)* | ![Activo](https://img.shields.io/badge/Status-Activo-brightgreen?style=flat) |
| **🟢 PROD** | *(agregar URL)* | ![Activo](https://img.shields.io/badge/Status-Activo-brightgreen?style=flat) |

</div>

---

## 15. 🔐 Variables de Entorno

```bash
# Base de datos
DB_URL=jdbc:postgresql://localhost:5432/profile_db
DB_USER=postgres
DB_PASSWORD=tu_password
DB_NAME=profile_db

# JWT (misma clave que auth-service)
JWT_SECRET=tu_secreto_jwt_minimo_32_caracteres

# Spring Profile
SPRING_PROFILES_ACTIVE=local   # local | test (H2) | qa | prod

# URL base del servicio (usada en emails)
APP_BASE_URL=http://localhost:1501

# Mail SMTP (requerido en todos los perfiles)
MAIL_HOST=smtp.example.com
MAIL_PORT=587
MAIL_USERNAME=tu_usuario_smtp
MAIL_PASSWORD=tu_password_smtp
```

> ⚠️ **Nunca subas el archivo `.env` al repositorio.** Está incluido en `.gitignore`. Las variables de producción se configuran como **Secrets** en GitHub Actions y se inyectan en Azure Container Apps.

> ⚠️ **`JWT_SECRET` debe ser idéntica** en `auth-service` y `profile-service`, ya que el `JwtAuthFilter` de este servicio valida los tokens emitidos por `auth-service`.

---

## 16. 📚 Referencias

- [Spring Boot 3.4 Reference Documentation](https://docs.spring.io/spring-boot/docs/3.4.3/reference/html/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [jjwt — Java JWT Library](https://github.com/jwtk/jjwt)
- [MapStruct 1.5 Reference Guide](https://mapstruct.org/documentation/stable/reference/html/)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [PostgreSQL 16 Documentation](https://www.postgresql.org/docs/16/)
- [Spring Mail (JavaMailSender)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mail/javamail/JavaMailSender.html)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [SonarCloud Documentation](https://docs.sonarcloud.io/)
- [Docker Documentation](https://docs.docker.com/)
- [Azure Container Apps](https://docs.microsoft.com/en-us/azure/container-apps/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Arquitectura Hexagonal — Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Lombok Project](https://projectlombok.org/)
- [BCrypt Password Encoding — Spring Security](https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html)

---

<div align="center">

### 🏆 Módulo 1 — Gestión de Perfiles

![Module](https://img.shields.io/badge/Module-1-blueviolet?style=for-the-badge)
![Team](https://img.shields.io/badge/Team-Grootyology-orange?style=for-the-badge)
![Project](https://img.shields.io/badge/Project-A.IBERT_ECI_Planner-blue?style=for-the-badge)
![Year](https://img.shields.io/badge/Year-2026-darkgreen?style=for-the-badge)

> 💡 **A.IBERT — ECI Planner** es un sistema académico inteligente diseñado para optimizar
> el rendimiento estudiantil mediante gestión académica estructurada e inteligencia artificial.

**🎓 Escuela Colombiana de Ingeniería Julio Garavito**

</div>
