<div align="center">

# 👤 AIBERT — Profile Service

### *"Gestión centralizada del perfil del usuario dentro de la plataforma AIBERT"*

---

### 🛠️ Stack Tecnológico

![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=for-the-badge&logo=postgresql&logoColor=white)

### ☁️ Infraestructura & Calidad

![Azure](https://img.shields.io/badge/Azure-Cloud-0078D4?style=for-the-badge&logo=microsoft-azure&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Container-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![SonarCloud](https://img.shields.io/badge/SonarCloud-Analysis-F3702A?style=for-the-badge&logo=sonarcloud&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-brightgreen?style=for-the-badge)

### 🏗️ Arquitectura

![Hexagonal](https://img.shields.io/badge/Architecture-Hexagonal-blueviolet?style=for-the-badge)
![Clean Architecture](https://img.shields.io/badge/Clean-Architecture-blue?style=for-the-badge)
![REST API](https://img.shields.io/badge/REST-API-009688?style=for-the-badge)

</div>

---

## 📑 Tabla de Contenidos

1. [👤 Integrantes](#1--integrantes)
2. [🎯 Objetivo del Microservicio](#2--objetivo-del-microservicio)
3. [⚡ Funcionalidades Principales](#3--funcionalidades-principales)
4. [📋 Estrategia de Versionamiento y Branches](#4--manejo-de-estrategia-de-versionamiento-y-branches)
5. [⚙️ Tecnologías Utilizadas](#5--tecnologias-utilizadas)
6. [🧩 Funcionalidad](#6--funcionalidad)
7. [📊 Diagramas](#7--diagramas)
8. [⚠️ Manejo de Errores](#8--manejo-de-errores)
9. [🧪 Evidencia de Pruebas y Ejecución](#9--evidencia-de-las-pruebas-y-como-ejecutarlas)
10. [🗂️ Organización del Código](#10--codigo-de-la-implementacion-organizado-en-las-respectivas-carpetas)
11. [🚀 Ejecución del Proyecto](#11--ejecucion-del-proyecto)
12. [☁️ CI/CD y Despliegue en Azure](#12--evidencia-de-cicd-y-despliegue-en-azure)
13. [🤝 Contribuciones](#13--contribuciones)

---

## 1. 👤 Integrantes

- **Equipo:** Grootyology

---

## 2. 🎯 Objetivo del microservicio

Este servicio es el que sabe todo sobre el usuario: quién es, qué estudia, qué quiere lograr. Se encarga del registro, del perfil personal y académico, de cambiar contraseña, de recuperarla si la olvidaste, y hasta de borrar la cuenta si querés.

El auth-service te deja entrar, pero este es el que guarda y gestiona tus datos. Están separados a propósito para que cada uno haga una sola cosa bien.

---

## 3. ⚡ Funcionalidades principales

<div align="center">

<table>
  <thead>
    <tr>
      <th>🧩 Funcionalidad</th>
      <th>¿Qué hace?</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Registro de Usuario</strong></td>
      <td>Crea la cuenta y manda un email para verificarla.</td>
    </tr>
    <tr>
      <td><strong>Verificación de Email</strong></td>
      <td>Activa la cuenta con el token que llegó al correo.</td>
    </tr>
    <tr>
      <td><strong>Gestión de Perfil Personal</strong></td>
      <td>Ver y actualizar nombre, foto y datos básicos.</td>
    </tr>
    <tr>
      <td><strong>Gestión de Perfil Académico</strong></td>
      <td>Carrera, semestre, metas y disponibilidad — lo que usan otros servicios como Gamificación.</td>
    </tr>
    <tr>
      <td><strong>Cambio de Contraseña</strong></td>
      <td>Cambiar la contraseña estando autenticado.</td>
    </tr>
    <tr>
      <td><strong>Recuperación de Contraseña</strong></td>
      <td>Si la olvidaste, te mandamos un email con un token para resetearla.</td>
    </tr>
    <tr>
      <td><strong>Eliminación de Cuenta</strong></td>
      <td>El usuario puede borrar su propia cuenta.</td>
    </tr>
    <tr>
      <td><strong>Administración de Usuarios</strong></td>
      <td>Los admins pueden ver, editar y cambiar roles de cualquier cuenta.</td>
    </tr>
  </tbody>
</table>

</div>

---

## 4. 📋 Manejo de Estrategia de versionamiento y branches

Usamos **Git Flow** para no pisarnos entre nosotros y tener siempre una versión estable lista.

### Ramas que manejamos

- `main` — lo que está en producción, no se toca directamente.
- `develop` — acá se integra todo antes de subir a main.
- `feature/*` — una rama por cada cosa que estemos haciendo.

Algunas ramas que usamos:
- `feature/profile-ci-cd`
- `feature/dockerizacion`
- `feature/nuevos-requerimientos`

### Cómo trabajamos

1. Crear rama `feature/*` desde `develop`.
2. Implementar y probar local.
3. Abrir PR hacia `develop`.
4. Cuando `develop` está estable, se mergea a `main`.

---

## 5. ⚙️ Tecnologías Utilizadas

| Tecnología | Para qué la usamos |
|------------|-------------------|
| **Java 21** | Lenguaje base. |
| **Spring Boot 3.4.3** | El framework que levanta todo. |
| **Spring Security** | Filtro JWT y configuración de seguridad. |
| **Spring Data JPA** | Para hablar con la base de datos sin tanto boilerplate. |
| **PostgreSQL** | Donde guardamos usuarios y tokens. |
| **MapStruct** | Mapeo entre dominio y persistencia sin escribir código a mano. |
| **Spring Mail (SMTP)** | Para mandar los emails de verificación y recuperación. |
| **Apache Maven** | Build y dependencias. |
| **Docker** | Para correr el servicio en cualquier lado. |
| **GitHub Actions** | El pipeline de CI/CD. |
| **SonarCloud** | Análisis de calidad del código. |
| **JaCoCo** | Ver qué tan bien cubrimos con los tests. |

---

## 6. 🧩 Funcionalidad

### 📝 Registro

Creás la cuenta con nombre, email y contraseña. El servicio guarda todo y te manda un email para verificar.

**Endpoint:** `POST /api/auth/register`

### 📦 Request

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricción | 📝 Descripción |
|--------|--------|:-------------:|---------------|
| email | String | Obligatorio | Tu correo |
| password | String | Obligatorio | Tu contraseña |
| fullName | String | Obligatorio | Tu nombre completo |

</div>

### 📦 Response

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | 📝 Descripción |
|--------|--------|---------------|
| userId | UUID | El ID del usuario recién creado |
| message | String | Confirmación de que todo salió bien |

</div>

---

### 👤 Perfil Personal

Ver y actualizar tus datos básicos.

**Endpoints:**
- `GET /api/profile` — ver tu perfil
- `PUT /api/profile` — actualizar tus datos

### 📦 Request (actualización)

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricción | 📝 Descripción |
|--------|--------|:-------------:|---------------|
| fullName | String | Opcional | Nombre actualizado |
| avatarUrl | String | Opcional | URL de tu foto de perfil |

</div>

---

### 🎓 Perfil Académico

Tu info académica: carrera, semestre, metas y disponibilidad. Otros servicios como Gamificación la usan para personalizar la experiencia.

**Endpoint:** `PUT /api/profile/academic`

### 📦 Request

<div align="center">

| 🏷️ Campo | 🗃️ Tipo | ⚠️ Restricción | 📝 Descripción |
|--------|--------|:-------------:|---------------|
| career | String | Obligatorio | Tu carrera |
| semester | Integer | Obligatorio | Semestre actual |
| academicGoal | String | Opcional | Qué querés lograr |
| availability | String | Opcional | Cuándo tenés tiempo |

</div>

---

### 🔑 Recuperación de Contraseña

Dos pasos: pedís el reset (te llega un email con token) y luego confirmás la nueva contraseña.

**Endpoints:**
- `POST /api/auth/forgot-password` — pedir el reset
- `POST /api/auth/reset-password` — confirmar la nueva contraseña con el token

---

### 🛡️ Administración de Usuarios

Solo para admins. Pueden ver todos los usuarios, editarlos y cambiarles el rol.

**Endpoints:**
- `GET /api/admin/users` — listar usuarios
- `PUT /api/admin/users/{id}` — editar usuario
- `PUT /api/admin/users/{id}/role` — cambiar rol

---

## 7. 📊 Diagramas

### 🧱 Diagrama de Clases

Cómo está organizado el código por capas y cómo se conectan los controladores con los casos de uso.

<div align="center">

![Diagrama_de_Clases.png](docs/uml/Diagrama_de_Clases.png)

</div>

---

### 🧩 Diagrama de Componentes

Cómo interactúan los componentes durante el registro y la gestión del perfil.

<div align="center">

![Diagrama_de_componentes.png](docs/uml/Diagrama_de_componentes.png)

</div>

---

### 🔁 Diagrama de Secuencia — Registro

El flujo completo del registro: desde que llegan los datos hasta que se guarda el usuario y sale el email de verificación.

<div align="center">

![Diagrama Secuencia Registro](docs/uml/Diagrama_secuencia_register.png)

</div>

---

### 🔁 Diagrama de Secuencia — Actualización de Perfil Personal

Cómo fluye la actualización de datos personales, validando que sea el dueño del perfil.

<div align="center">

![Diagrama Secuencia Update Profile](docs/uml/Diagrama_secuencia_updateProfile.png)

</div>

---

### 🔁 Diagrama de Secuencia — Actualización de Perfil Académico

El flujo de actualización de info académica que luego consumen otros servicios.

<div align="center">

![Diagrama Secuencia Save Academic Profile](docs/uml/Diagrama_secuencia_saveAcademicProfile.png)

</div>

---

## 8. ⚠️ Manejo de Errores

Hay un `@ControllerAdvice` que atrapa todos los errores y devuelve respuestas limpias y consistentes, sin exponer nada interno.

<div align="center">

| 🔢 Código HTTP | ⚠️ Cuándo pasa |
|:-------------:|:------------|
| **400 Bad Request** | Faltan campos, el formato está mal o la contraseña es incorrecta. |
| **401 Unauthorized** | Token JWT inválido o expirado. |
| **404 Not Found** | No se encontró el usuario. |
| **409 Conflict** | El email ya está registrado. |
| **500 Internal Server Error** | Algo explotó en el servidor. |

</div>

---

## 9. 🧪 Evidencia de Pruebas y Ejecución

Tenemos pruebas unitarias para todo lo importante:

- `RegisterService` — registro y validaciones.
- `UpdateProfileService` — actualización de perfil.
- `AcademicProfileService` — perfil académico.
- `PasswordResetService` — recuperación de contraseña.
- `AdminUserService` — administración de usuarios.
- `DeleteAccountService` — eliminación de cuenta.
- `AuthController`, `ProfileController`, `AdminController` — los endpoints.
- `GlobalExceptionHandler` — que los errores se manejen bien.
- `JwtAuthFilter` — el filtro de autenticación.
- `UserRepositoryAdapter` — el adaptador de persistencia.

### 🚀 Cómo correr las pruebas

```bash
mvn clean test
```

Para ver el reporte de cobertura con JaCoCo:

```bash
mvn clean verify
# El reporte queda en: target/site/jacoco/index.html
```

---

## 10. 🗂️ Organización del Código (Scaffolding)

```
profile-service/
│
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/aibert/dosw/
│   │   │   ├── 📁 application/                     # 🔵 CAPA DE APLICACIÓN
│   │   │   │   ├── 📁 dto/
│   │   │   │   │   ├── 📁 request/                 # RegisterRequestDTO, UpdateProfileDTO, AcademicProfileDTO, PasswordChangeDTO, etc.
│   │   │   │   │   └── 📁 response/                # RegisterResponseDTO, AcademicProfileResponseDTO, UserSummaryDTO, etc.
│   │   │   │   └── 📁 service/                     # RegisterService, UpdateProfileService, AcademicProfileService,
│   │   │   │                                       # PasswordResetService, AdminUserService, DeleteAccountService, FileUploadService
│   │   │   │
│   │   │   ├── 📁 config/                          # ⚙️ JwtAuthFilter, SecurityConfig, SwaggerConfig
│   │   │   │
│   │   │   ├── 📁 domain/                          # 🟢 CAPA DE DOMINIO
│   │   │   │   ├── 📁 exceptions/                  # EmailAlreadyRegistered, UserNotFound, InvalidPassword, InvalidToken
│   │   │   │   ├── 📁 model/user/                  # User, Role, UserStatus, Career, AcademicGoal, Availability,
│   │   │   │   │                                   # EmailVerificationToken, PasswordResetToken
│   │   │   │   └── 📁 ports/in/                    # RegisterUseCase, UpdateProfileUseCase, AcademicProfileUseCase,
│   │   │   │                                       # PasswordResetUseCase, AdminUserUseCase, DeleteAccountUseCase
│   │   │   │
│   │   │   ├── 📁 entrypoints/                     # 🔴 CAPA DE ENTRADA
│   │   │   │   ├── 📁 rest/controller/             # AuthController, ProfileController, AdminController
│   │   │   │   └── 📁 advice/                      # GlobalExceptionHandler
│   │   │   │
│   │   │   ├── 📁 infrastructure/                  # 🟠 CAPA DE INFRAESTRUCTURA
│   │   │   │   ├── 📁 adapters/adapter/            # UserRepositoryAdapter, TokenRepositoryAdapter, PasswordResetTokenAdapter
│   │   │   │   └── 📁 adapters/persistence/
│   │   │   │       ├── 📁 entity/                  # UserEntity, EmailVerificationTokenEntity, PasswordResetTokenEntity
│   │   │   │       ├── 📁 mapper/                  # UserPersistenceMapper, TokenPersistenceMapper, PasswordResetTokenMapper
│   │   │   │       └── 📁 repository/              # UserJpaRepository, TokenJpaRepository, PasswordResetTokenJpaRepository
│   │   │   │
│   │   │   ├── 📁 infrastructure/external/email/   # SmtpEmailService — el que manda los correos
│   │   │   │
│   │   │   └── ProfileServiceApplication
│   │   │
│   │   └── 📁 resources/                           # application.yml (perfiles: local, qa, prod)
│   │
│   └── 📁 test/                                    # 🧪 Pruebas unitarias
│
└── pom.xml
```

---

## 11. 🚀 Ejecución del Proyecto

### 📋 Qué necesitás antes de arrancar
- **Java 21**
- **Maven 3.8+**
- **PostgreSQL** corriendo (o tirá Docker)
- Una cuenta SMTP para los emails
- Las variables de entorno del `.env.example`

### Variables de entorno

```env
DB_URL=jdbc:postgresql://localhost:5432/profile_db
DB_USER=postgres
DB_PASSWORD=tu_password
JWT_SECRET=tu_secreto_jwt
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=tu_email@gmail.com
MAIL_PASSWORD=tu_app_password
APP_BASE_URL=http://localhost:8081
```

### 🛠️ Opción 1: Maven directo

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

📍 **Local:** `http://localhost:8081`
📚 **Swagger:** `http://localhost:8081/swagger-ui.html`

### 🐳 Opción 2: Docker Compose

```bash
docker-compose up --build -d
```

---

## 12. ☁️ CI/CD y Despliegue en Azure

El pipeline se activa solo con cada push o PR a `develop` o `main`. Los pasos son:

1. **Compilation** — compila el proyecto.
2. **Tests** — corre los tests con H2 en memoria y publica resultados.
3. **Analysis** — genera el reporte JaCoCo y lo manda a SonarCloud.
4. **Build & Push Image** — construye la imagen Docker y la sube a `ghcr.io`.
5. **Deploy to QA** — despliega en Azure Container Apps cuando hay push a `develop`.
6. **Deploy to PROD** — despliega en producción cuando hay push a `main`.

### Secrets que necesitás configurar en GitHub

| Secret | Para qué |
|--------|----------|
| `SONAR_TOKEN` | Análisis con SonarCloud |
| `AZURE_CREDENCIALES_QA` | Deploy en QA |
| `AZURE_CREDENCIALES_PROD` | Deploy en producción |
| `GHCR_TOKEN` | Subir imagen a GitHub Container Registry |

---

## 13. 🤝 Contribuciones

Trabajamos con **Scrum** en iteraciones cortas. `main` y `develop` están protegidas — todo entra por PR y tiene que pasar el pipeline completo (compilación, tests y SonarCloud) antes de mergearse.

<div align="center">

### 🏆 Proyecto AIBERT

![Course](https://img.shields.io/badge/Course-DOSW-orange?style=for-the-badge)
![Year](https://img.shields.io/badge/Year-2026-blue?style=for-the-badge)

</div>
