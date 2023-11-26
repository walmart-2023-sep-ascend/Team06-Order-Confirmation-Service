FROM openjdk:17
EXPOSE 6002
ADD target/order-service.jar order-service.jar
ENTRYPOINT ["java","-jar","order-service.jar"]