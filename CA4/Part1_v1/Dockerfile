#FROM ubuntu:latest
#LABEL authors="franciscomartins"
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

RUN chmod +x gradlew
RUN ./gradlew build

EXPOSE 59001

CMD ["java", "-cp", "/app/build/libs/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]

#open docker desktop
#docker build -t badjaras .
#docker run -p 59001:59001 badjaras
#in new terminal window: ./gradlew runClient