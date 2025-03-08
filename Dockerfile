FROM maven:3.8.5-openjdk-17 AS build

# Configuramos el directorio de trabajo
WORKDIR /app

# Copiamos el pom.xml para descargar dependencias
COPY pom.xml .
# Descargamos las dependencias en una capa separada para aprovechar la caché
RUN mvn dependency:go-offline -B

# Copiamos el código fuente
COPY src ./src

# Compilamos y empaquetamos la aplicación
RUN mvn package -DskipTests

# Usamos una imagen base de JRE para la ejecución
FROM eclipse-temurin:17-jre

# Metadatos
LABEL maintainer="Jair"
LABEL description="Prueba técnica Spring Boot con arquitectura hexagonal"

# Configuramos el directorio de trabajo
WORKDIR /app

# Copiamos el archivo JAR de la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto que usa la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]