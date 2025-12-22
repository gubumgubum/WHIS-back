# 1. 빌드 스테이지 (JDK 설치된 환경)
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
# 권한 부여 및 빌드 (테스트 제외로 속도 향상)
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 2. 실행 스테이지 (가벼운 JRE 환경)
FROM openjdk:17-jdk-slim
WORKDIR /app
# 빌드 스테이지에서 생성된 jar 파일만 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 3. 실행 (포트는 보통 8080)
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]