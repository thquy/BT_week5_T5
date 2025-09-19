# Sử dụng Maven để build dự án
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Sử dụng Tomcat để chạy WAR
FROM tomcat:9.0.89-jdk17
COPY --from=build /app/target/BT_C7_T5.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
