# Class Assignment 4, Part 2: Docker Compose

## Introduction

The aim of this class assignment is to leverage Docker to construct two images and a docker-compose file, and subsequently execute a container with these images. For this assignment, two Dockerfiles and a docker-compose file were developed. One image is designated for running the web application and the second one is for hosting an H2 database. The docker-compose file is responsible for creating the two images and running the containers. Bellow is a step-by-step guide to creating the three files you need in your Part 2 folder:

## Dockerfile for Web Application

The initial Dockerfile created was for the web application. This Dockerfile is tasked with creating an image that runs the application. It first installs additional utilities, clones the Spring Boot application repository, and then builds the application. The Dockerfile is displayed below:

```Dockerfile
FROM tomcat

# Install additional utilities
RUN apt-get update -y && apt-get install -y git unzip

WORKDIR /app/

# Clone your Spring Boot application repository
RUN git clone https://github.com/FranciscoRamosMartins/devops-23-24-JPE-1231865.git

# Set the working directory
WORKDIR /app/devops-23-24-JPE-1231865/CA2/Part2

# Ensure the gradlew script is executable and build the application
 RUN chmod +x ./gradlew && ./gradlew clean build

# Configure the container to run as an executable
 ENTRYPOINT ["./gradlew"]
 CMD ["bootRun"]
```

## Dockerfile for Database

This Dockerfile is tasked with creating an image that runs the H2 database. It first installs the necessary utilities, downloads the H2 database, and then starts the H2 server:

```Dockerfile
FROM ubuntu

RUN apt-get update && \
  apt-get install -y openjdk-17-jdk-headless && \
  apt-get install unzip -y && \
  apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

# Download H2 Database and run it
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O /opt/h2.jar

EXPOSE 8082
EXPOSE 9092

# Start H2 Server
CMD java -cp /opt/h2.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists
```

## Docker-compose File

This file is responsible for creating the two images and running the containers. It specifies the services that will be created, the images that will be used, the ports that will be exposed, and the volumes that will be mounted:

```yaml
version: '3.8'

services:
  db:
    build:
      context: .
      dockerfile: Dockerfile-db
    container_name: db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - h2-data:/app/db-backup

  web:
    build:
      context: .
      dockerfile: Dockerfile-web
    container_name: web
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:h2:tcp://db:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

volumes:
  h2-data:
    driver: local
```
After the docker-compose file is created we can build the images and run the containers by running the following command (Don't forget - you need to be in the Part2 folder and have Docker Desktop running in the background):

```bash
docker-compose up --build
```

After starting the containers, the web app can be accessible through your browser:
```
http://localhost:8080
```

The H2 database can be accessed at:

```
http://localhost:8082
```
The default username is "sa" and the password is blank. In JBDC URL, use the following URL: jdbc:h2:tcp://db:9092/./jpadb

## Pushing Docker Images to Docker Hub

Push the Web docker image to Docker Hub by running the following command:

```bash
docker tag ca4-part2:web FranciscoRamosMartins/devops_23_24:CA4_Part2_web
```

```bash
docker push FranciscoRamosMartins/devops_23_24:CA4_Part2_web
```

And finally push the Database docker image to Docker Hub by running the following command:

```bash
docker tag ca4-part2:db FranciscoRamosMartins/devops_23_24:CA4_Part2_db
```

```bash
docker push FranciscoRamosMartins/devops_23_24:CA4_Part2_db
```


## Alternative Solution

An alternative solution for this assignment would be to deploy the app and the database in a Kubernetes cluster. This would allow for better scalability and availability of the app. The app could be deployed in a pod and the database in another pod. The app would then connect to the database using the service name. This would allow for better separation of concerns and better management of the app and the database.
After pushing the docker images to Docker Hub, we would need to create a Kubernetes deployment file for the app and the database. The deployment file would look something like this:

Web app deployment file:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
   name: ca4-part2-web
spec:
   replicas: 1
   selector:
      matchLabels:
         app: ca4-part2-web
   template:
      metadata:
         labels:
            app: ca4-part2-web
      spec:
         containers:
            - name: ca4-part2-web
              image: FranciscoRamosMartins/devops_23_24:CA4_Part2_web
              ports:
                 - containerPort: 8080

 ```

Web app service file:
```yaml
apiVersion: v1
kind: Service
metadata:
   name: ca4-part2-web
spec:
   type: NodePort
   ports:
      - port: 8080
        targetPort: 8080
        nodePort: 30080
   selector:
      app: ca4-part2-web
```

Database deployment file:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
   name: ca4-part2-db
spec:
   replicas: 1
   selector:
      matchLabels:
         app: ca4-part2-db
   template:
      metadata:
         labels:
            app: ca4-part2-db
      spec:
         containers:
            - name: ca4-part2-db
              image: FranciscoRamosMartins/devops_23_24:CA4_Part2_db
              ports:
                 - containerPort: 8082
                 - containerPort: 9092
```

Database service file:
```yaml
apiVersion: v1
kind: Service
metadata:
   name: ca4-part2-db
spec:
   type: NodePort
   ports:
      - port: 8082
        targetPort: 8082
        nodePort: 30082
      - port: 9092
        targetPort: 9092
        nodePort: 30092
   selector:
      app: ca4-part2-db
```
Unlike Docker, Kubernetes uses a declarative approach to manage the state of the cluster. This means that we define the desired state of the cluster in a YAML file and Kubernetes will make sure that the cluster is in that state. This allows for better management of the cluster and better scalability and availability of the app.
It is not uncommon for Docker and Kubernetes to be used together. Docker is used to build the images and Kubernetes is used to deploy and manage the containers. This allows for better separation of concerns and better management of the app and the database.

## Conclusion

In conclusion, this assignment was a great opportunity to learn more about Docker and how it can be used to deploy an app and a database. Docker is a great tool for building, shipping, and running applications in containers. It allows for better separation of concerns and better management of the app and the database. Kubernetes is another great tool that can be used to deploy and manage containers in a cluster. It allows for better scalability and availability of the app.