# Class Assignment 4, Part 1: Containers with Docker

## Introduction

This tutorial will guide you through the process of creating a Docker image for a basic demo application. The application is a simple chat server that allows multiple clients to connect and chat with each other. The chat server is built using Java and Gradle. For this tutorial, you will need to install Docker Desktop, which you can download from the official Docker website [here](https://www.docker.com/products/docker-desktop).

## Version 1 - Building the Basic Demo Application "inside" the Dockerfile

The goal here is to package and execute the basic demo application in a Docker container. 

First, clone the repository:

```bash
git clone https://bitbucket.org/pssmatos/gradle_basic_demo/
```

Next, create a Dockerfile to build the basic demo application inside the Docker image. The Dockerfile will need the following instructions:

- `FROM`: Specifies the base image to start the build process.
- `WORKDIR`: Sets the working directory.
- `COPY`: Copies the project files into the Docker image.
- `RUN`: Builds the project using Gradle.
- `EXPOSE`: Exposes the port used by the application.
- `CMD`: Specifies the command to run the basic demo application.

Let's create the Dockerfile:

```Dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

RUN chmod +x gradlew
RUN ./gradlew build

EXPOSE 59001

CMD ["java", "-cp", "/app/build/libs/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
```

After creating the Dockerfile, you need to open Docker Desktop and leave it running in the background. Then, run the following command in the terminal to build the Docker image (please ensure you are in the project directory):

```bash
docker build -t basic-demo .
```

Then, run the following command to start the container:

```bash
docker run -p 59001:59001 basic-demo
```

Finally, open two new terminal windows and run the following command on both to check if the image was created successfully and the chat app is working correctly:

```bash
./gradlew runClient
```

You can use the following command to see the container running:

```bash
docker ps
```

To stop the container, use the following command:

```bash
docker stop <container_id>
```

To remove the container, use the following command:

```bash
docker rm <container_id>
```

To remove the image, use the following command:

```bash
docker rmi <image_id>
```

Alternatively, you can use Docker Desktop to see the container running, stop and remove the container and image.

## Version 2 - Building the Basic Demo Application in Your Host Computer and Copy the Jar File ”into” the Dockerfile

For this version, we will build the application on our host machine and then copy the jar file into the Docker image. This requires a different Dockerfile.

Here's what Dockerfile should look like:

```Dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

EXPOSE 59001

CMD ["java", "-jar", "basic_demo-0.1.0.jar"]
```

Once again, you need to open Docker Desktop and leave it running in the background. Then, run the following command in the terminal to build the Docker image (please ensure you are in the project directory):

```bash
docker build -t basic-demo-v2 .
```

After building the image, you can run the container with the following command:

```bash
docker run -p 59001:59001 basic-demo-v2
```

## Publishing the Image

If you wish to publish the image, first log in to Docker Hub:

```bash
docker login
```

Then, tag the image:

```bash
docker tag basic-demo-v2 FranciscoRamosMartins/basic-demo-v2
```

Finally, push the image:

```bash
docker push FranciscoRamosMartins/basic-demo-v2
```

The image should now be available on Docker Hub.

## Conclusion

This tutorial has shown you how to create a Docker image for a basic demo application. You have learned how to build the application inside the Dockerfile and how to copy the jar file into the Docker image. You have also learned how to run the container and publish the image to Docker Hub. I hope you found this tutorial helpful. If you have any questions or feedback, please feel free to reach out to me. Thank you for reading!