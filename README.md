# Prueba Técnica - Aplicación Spring Boot con Arquitectura Hexagonal

Este proyecto demuestra la implementación de una aplicación Spring Boot con arquitectura hexagonal que incluye una API REST para una lista de tareas (To-Do) y un chat usando WebSockets.

## Requerimientos del Proyecto

La prueba técnica requería desarrollar:
- Una API REST con funcionalidad básica, como un To-Do List
- Implementación de WebSockets, idealmente un pequeño chat
- Uso de arquitectura hexagonal
- Uso de JUnit y Mockito para testing
- Uso de Docker para la entrega del proyecto

### Decisiones Técnicas
- **Base de datos H2**: elegi usar esta base de datos en memoria para una implementacion rapida
- **WebSockets**: Implementados para comunicación en tiempo real en el chat
- **DTOs**: Utilizados para desacoplar la API externa del modelo de dominio
- **Pruebas Unitarias**: Enfocadas en la lógica de negocio usando Mockito

## Estructura del Proyecto

```
/src/main/java/jair/pruebatecnica
  ├── application/            # Capa de aplicación (Casos de uso)
  │   ├── service/           # Servicios que implementan la lógica de negocio
  │   ├── dto/               # Objetos de transferencia de datos
  │   ├── ports/             # Interfaces (puertos)
  │     ├── api/             # Puertos primarios (API)
  │     ├── spi/             # Puertos secundarios (SPI)
  ├── domain/                # Capa de dominio (Modelo de negocio)
  │   ├── model/             # Entidades del dominio
  ├── infrastructure/        # Capa de infraestructura (Adaptadores)
  │   ├── persistence/       # Adaptadores para la base de datos
  │   ├── web/               # Adaptadores para la API REST
  │   ├── websocket/         # Adaptadores para WebSockets
  │   ├── config/           # Configuraciones
```

## Opciones de Entrega

### 1. Repositorio Git
El código está disponible en GitHub: https://github.com/Jair0305/todoandchatapi
- Incluye toda la documentación
- Fácil de clonar y ejecutar

### 2. Archivo Comprimido
Se incluye un archivo ZIP con:
- Código fuente completo
- Colección de Postman para pruebas
- Documentación
- Dockerfile

### 3. Imagen Docker
La aplicación está disponible como imagen Docker. Se proporcionan dos opciones:

#### Opción 1: Construir la imagen localmente
Si prefieres construir la imagen desde el código fuente:
```bash
docker build -t pruebatecnica .
docker run -p 8080:8080 pruebatecnica
```

#### Opción 2: Usar la imagen proporcionada
Se adjunta el archivo de imagen Docker (pruebatecnica.tar) que puede ser cargado directamente:
```bash
# Cargar la imagen desde el archivo
docker load < pruebatecnica.tar

# Ejecutar el contenedor
docker run -p 8080:8080 pruebatecnica
```

## Prueba de la Aplicación

### Archivos de Postman Incluidos
Para facilitar las pruebas, se incluyen dos archivos en el directorio `/postman/`:

1. **PruebaTecnica.postman_collection.json**
   - Colección completa con todos los endpoints
   - Ejemplos de requests para cada operación
   - Documentación detallada de cada endpoint
   - Ejemplos de payloads para POST/PUT

2. **PruebaTecnica.postman_environment.json**
   - Variables de entorno preconfiguradas
   - Configuración base para localhost
   - Fácilmente adaptable para otros entornos

Para usar la colección:
1. Importar ambos archivos en Postman
2. Seleccionar el environment "Prueba Técnica - Local"
3. Los endpoints están listos para usar

### Endpoints Principales

#### To-Do List
- **Listar tareas**: GET `/api/todos`
- **Crear tarea**: POST `/api/todos`
```json
{
    "title": "Completar prueba técnica",
    "description": "Implementar arquitectura hexagonal",
    "completed": false
}
```

#### Chat
- **Enviar mensaje**: POST `/api/chat/messages`
```json
{
    "content": "Hola, este es un mensaje de prueba",
    "sender": "Usuario1",
    "type": "CHAT"
}
```

### WebSocket
- Conexión: `/ws`
- Envío de mensajes: `/app/chat.sendMessage`
- Suscripción: `/topic/public`

## Ejecución y Pruebas

### Local
```bash
mvn clean package
java -jar target/pruebatecnica-0.0.1-SNAPSHOT.jar
```

### Docker
```bash
docker build -t pruebatecnica .
docker run -p 8080:8080 pruebatecnica
```

### Tests
```bash
mvn test
```

## Aprendizajes y Mejoras Futuras

Durante el desarrollo de este proyecto:
- Profundicé en la implementación práctica de la arquitectura hexagonal
- Aprendí a integrar WebSockets en una arquitectura limpia
- Identifiqué áreas de mejora como:
  - Implementar caché para optimizar consultas frecuentes
  - Añadir más pruebas de integración
  - Mejorar la documentación con Swagger/OpenAPI, aunque si uso mucho swagger en otros proyectos, no considere necesario en este proyecto, debido a la simplicidad y doumentacion puesta en este readme.



Desarrollado por Jair Chavez Islas
- jair@nightly.software
- chamaco_03@hotmail.es
- +52 462 245 96 48
