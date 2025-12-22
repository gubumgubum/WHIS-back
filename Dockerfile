# 1. 빌드 스테이지
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 2. 실행 스테이지 (이 부분을 수정합니다)
# 기존 openjdk:17-jdk-slim 대신 안정적인 eclipse-temurin 이미지를 사용하세요.
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 빌드 스테이지에서 생성된 jar 파일 복사
# (build/libs 폴더에 plain.jar 등이 같이 생성될 수 있으므로,
#  보통 실행 가능한 jar만 가져오기 위해 아래와 같이 설정하는 것이 안전합니다.)
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# 3. 실행
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]