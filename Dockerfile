# Sử dụng image Tomcat 9 với JDK 11 (ổn định cho JSP/Servlet + SQL Server JDBC)
FROM tomcat:9.0-jdk11

# Xóa ứng dụng mặc định của Tomcat (ROOT)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR từ target của Maven vào thư mục webapps/ROOT.war
COPY target/BT_C7_T5.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080 cho Render
EXPOSE 8080

# Chạy Tomcat
CMD ["catalina.sh", "run"]
