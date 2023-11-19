FROM openjdk:17
EXPOSE 6002
ADD target/order-service-docker.jar order-service-docker.jar
ENTRYPOINT ["java","-jar","order-service-docker.jar"]