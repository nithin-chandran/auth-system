FROM gradle:8.4-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle bootJar --no-daemon

FROM openjdk:17-jdk-slim AS runtime

WORKDIR /app

COPY --from=build /app/application/server/build/libs/auth-system-server-all.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "auth-system-server-all.jar"]
