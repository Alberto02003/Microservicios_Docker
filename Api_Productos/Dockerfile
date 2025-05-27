FROM openjdk:23-jdk-slim
ARG JAR_FILE=/target/Api_Productos-0.0.1.jar
WORKDIR /app
COPY ${JAR_FILE} App_Productos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "App_Productos.jar"]