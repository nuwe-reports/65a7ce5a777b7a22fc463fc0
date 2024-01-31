 # Etapa 1: Construir y ejecutar los tests
FROM maven:3.6.3-openjdk-17-slim AS test
LABEL authors="Iván Azagra"
WORKDIR /app

 # Copiamos solo los archivos necesarios para descargar las dependencias y ejecutar tests
COPY pom.xml .
COPY src/test/ src/test
COPY src/main src/main

 # Descargamos las dependencias y ejecutamos los tests
RUN mvn clean test

 # Etapa 2: Construir la aplicación
FROM maven:3.6.3-openjdk-17-slim AS build
WORKDIR /app

 # Copiamos tod el código fuente y el resultado de la etapa de tests
COPY . .

 # Copiamos solo el resultado necesario de la etapa de tests
COPY --from=test /app/target /app/target/

 # Construimos la aplicación
RUN mvn clean install

FROM jetty:9.2.10

 # Copiamos el artefacto compilado desde la etapa de compilación
COPY --from=build /app/target/accenture-techhub-0.0.1-SNAPSHOT /var/lib/jetty/webapps/ROOT


