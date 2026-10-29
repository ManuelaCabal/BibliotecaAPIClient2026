# BibliotecaAPIClient26

Cliente MVC con Spring Boot para consumir la API `BibliotecaAPI26` mediante DTOs.

## Características

- Spring Boot 3 + Thymeleaf + Bootstrap 5.
- Estructura MVC clara: `controller`, `service`, `dto`, `mapper`, `web/form`.
- CRUD visual para `autores`, `libros`, `paises` y `premios`.
- Cliente HTTP con `RestClient` hacia `http://localhost:8080/api`.
- Configuración centralizada de rutas DTO en `src/main/resources/application.yml`.

## Requisitos

- Java 17 o superior.
- Maven 3.9 o superior.
- Tener levantada la API `BibliotecaAPI26` en el puerto `8080`.

## Ejecutar

```bash
cd /Users/manuelacabal/BibliotecaAPIClient26
mvn spring-boot:run
```

La aplicación cliente se publica por defecto en `http://localhost:8090`.

## Rutas esperadas de la API

Este cliente asume, por defecto, estas rutas:

- `GET/POST /api/autores/DTO`
- `GET/PUT/DELETE /api/autores/DTO/{id}`
- `GET/POST /api/libros/DTO`
- `GET/PUT/DELETE /api/libros/DTO/{id}`
- `GET/POST /api/paises/DTO`
- `GET/PUT/DELETE /api/paises/DTO/{id}`
- `GET/POST /api/premios/DTO`
- `GET/PUT/DELETE /api/premios/DTO/{id}`
- `GET /api/series/DTO`

Si tu API usa otra variante de endpoints para crear, editar o consultar detalles, solo ajusta los valores bajo `biblioteca.api.paths` en `application.yml`.

## Estructura

```text
src/main/java/com/biblioteca/bibliotecaapiclient26
├── config
├── controller
├── dto
├── mapper
├── service
└── web/form
```
