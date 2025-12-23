# 1. 빌드 스테이지
FROM gradle:8.7-jdk17 AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 2. 런타임 스테이지
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# 3. 실행
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
