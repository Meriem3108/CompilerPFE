# Use a more recent Maven version
FROM maven:3.8.4-openjdk-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies first (go offline)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and package the application
COPY src ./src
RUN mvn package -DskipTests

# Use the official OpenJDK image for running the app
FROM openjdk:17-jdk-alpine

# Set environment variables for the executable paths
ENV TEMPLATE_COMPILER_PATH=/app/templatefinal2.exe
ENV SCENARIO_COMPILER_PATH=/app/testFinal.exe

# Copy the jar from the build stage
COPY --from=build /app/target/testpfe-0.0.1-SNAPSHOT.jar /testpfe-0.0.1.jar

# Copy the executable files into the container
COPY templatefinal2.exe /app/templatefinal2.exe
COPY testFinal.exe /app/testFinal.exe

# Entry point for running the application
ENTRYPOINT ["java", "-jar", "/testpfe-0.0.1.jar"]
