
# Stage 1: Build the Java application
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY src ./src

RUN javac -d out src/main/java/com/example/library/*.java

# Stage 2: Run the application
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/out ./out

RUN mkdir -p data

CMD ["java", "-cp", "out", "com.example.library.Main"]
