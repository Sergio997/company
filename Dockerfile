FROM openjdk:11
COPY target/company-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/company-0.0.1-SNAPSHOT.jar"]