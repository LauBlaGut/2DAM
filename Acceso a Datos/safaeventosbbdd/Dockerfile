#1. Construir la aplicación
FROM eclipse-temurin:23-jdk AS builder

#Definir la carpeta de trabajo
WORKDIR /app

# Copiar los archivos del proyecto al contenedor DOCKER
COPY . .

# Ejecutar la compilación de Maven para empaquetar la aplicación
RUN ./mvnw clean package -DskipTests

#2. Crear la imagen final para ejecutar la aplicación
FROM eclipse-temurin:23-jre

# Definir la carpeta de trabajo
WORKDIR /app

# Copiar el archivo JAR construido desde la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto en el que la aplicación escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]