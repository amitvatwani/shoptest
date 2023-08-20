FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar shopcreditmanagementsystem.jar
ENTRYPOINT ["java","-jar","/shopcreditmanagementsystem.jar"]
EXPOSE 8080