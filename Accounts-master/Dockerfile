FROM openjdk:11-jdk-slim
EXPOSE 8081
COPY . app/
RUN cd app; ./mvnw -Dmaven.test.skip package
ENTRYPOINT ["java","-jar","/app/target/accounts_service.jar"]
