#LABEL authors="franciscomartins"
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

EXPOSE 59001

CMD ["java", "-jar", "basic_demo-0.1.0.jar"]

#open docker desktop
#docker build -t badjarasv2 .
#docker run -p 59001:59001 badjarasv2