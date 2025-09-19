# Stage 1: Build với Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Chạy với Tomcat
FROM tomcat:9.0-jdk17-temurin
# Xóa app mặc định ROOT
RUN rm -rf /usr/local/tomcat/webapps/ROOT
# Copy WAR từ stage build
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
