# 🛒 Proyecto de Microservicios: Gestión de Productos y Compras

## 💡 ¿Qué se ha implementado?

---

### ✅ Microservicio de Productos y Compras

Desplegado en **Docker**, desarrollado con **Spring Boot** y **MySQL**. Este servicio gestiona el **catálogo de productos** y las **compras realizadas**.

#### 🔹 Endpoints disponibles:
- `GET /productos`
- `POST /producto`
- `PUT /producto/{id}`
- `DELETE /producto/{id}`
- `POST /compra`

---

### ✅ Microservicio de Autenticación de Usuarios

También desplegado en **Docker** con **Spring Boot** y **MySQL**. Se encarga del **registro**, **login** (con contraseñas cifradas) y **validación de credenciales**.

#### 🔹 Endpoints clave:
- `POST /login`
- `GET /user`

---

### ✅ Servidor FTP

Desplegado en Docker. Cada usuario tiene su propio archivo de registro de compras:

📄 Formato: `username_compras.txt`

#### 📋 Contenido del archivo:
- Fecha y hora de la compra
- Número de productos
- Precio total

Las compras se **agregan continuamente**, sin sobrescribir registros anteriores.

---

### ✅ Cliente con Interfaz Gráfica

Desarrollada con **Java** + **Retrofit** para interactuar con los servicios REST.

#### 🎭 Funcionalidad según el rol:
- 👤 **Usuarios** → pueden realizar compras
- 🛠️ **Administradores** → pueden gestionar productos (añadir, modificar, eliminar)

---

## 🔧 Tecnologías utilizadas

- Docker
- Spring Boot
- Java (con Retrofit para el cliente GUI)
- FTP Server
- MySQL
- REST APIs

---

> Esta arquitectura simula un entorno **realista de desarrollo distribuido**, integrando múltiples tecnologías y servicios para reflejar un sistema de compras en producción.
