# Class Assignment 5 - Jenkins Pipeline for CI/CD

## Overview

This assignment focuses on creating Jenkins pipelines to automate the build, test, and deployment processes for Java applications, including pushing Docker images to DockerHub. We begin with a basic pipeline for core tasks such as Checkout, Assemble, Test, and Archive. We then expand this into a more advanced pipeline that includes Docker image creation and publication.

## Initial Setup

### Downloading and Installing Jenkins

1.  Download the latest version of Jenkins (2.452.2 LTS) from the [Jenkins official website](https://www.jenkins.io/).
2.  Navigate to the directory where the `jenkins.war` file is located.
3.  Start Jenkins by running:
    ```bash
    java -jar jenkins.war --httpPort=[port number]
    ```

### Accessing Jenkins

4. Open a web browser and navigate to `http://localhost:[port number]` to access the Jenkins dashboard.

### Installing Required Plugins

5. From the Jenkins dashboard, go to "Manage Jenkins" -> "Manage Plugins".
6. Install the following plugins:


    - HTML Publisher
    - Docker Pipeline
    - Docker Commons Plugin
    - Docker Plugin
    - Docker API Plugin

### Create a New Pipeline

7. On the Jenkins dashboard, click "New Item" and create a new pipeline.
8. Add the provided pipeline scripts for Part 1 and Part 2 in the pipeline configuration section.

## Jenkinsfile for Part 1

### Script
```groovy
pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/FranciscoRamosMartins/devops-23-24-JPE-1231865.git'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the code...'
                git url: "${REPO_URL}", branch: 'master'
            }
        }

        stage('Assemble') {
            steps {
                dir('CA2/Part1') {
                    echo 'Assembling the application...'
                    sh './gradlew assemble'
                }
            }
        }

        stage('Test') {
            steps {
                dir('CA2/Part1') {
                    echo 'Running unit tests...'
                    sh './gradlew test'
                    junit 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Archive') {
            steps {
                dir('CA2/Part1') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
    }
}
```

### Detailed Explanation

- **Pipeline Structure:** The pipeline starts with `agent any`, indicating it can run on any available Jenkins agent (a machine that runs jobs).


- **Environment Variables:** Defines `REPO_URL` to specify the GitHub repository URL where the source code is hosted.


- **Stages:**
    - **Checkout:** This stage checks out the code from the specified GitHub repository. It uses the `git` step to clone the repository and check out the 'master' branch.
    - **Assemble:** In this stage, the pipeline navigates to the `CA2/Part1` directory and runs the Gradle wrapper script (`./gradlew assemble`) to compile the application. This step ensures that the code is built and ready for testing.
    - **Test:** This stage runs unit tests by executing the `./gradlew test` command within the `CA2/Part1` directory. After running the tests, it uses the `junit` command to publish the test results. This step ensures that the application is tested and the results are available for review.
    - **Archive:** Finally, this stage archives the compiled JAR files for future use. It uses the `archiveArtifacts` step to store the JAR files and applies fingerprinting to track the artifacts' changes over time.

## Jenkinsfile for Part 2

### Script
```groovy
pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = '1a92e342-7273-4651-957d-895877a5eac9'
        DOCKER_IMAGE = 'badjaras/springbootapp'
        DOCKER_TAG = "${env.BUILD_ID}"
        PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"
    }

    stages {
        stage('Check Docker') {
            steps {
                echo 'Checking Docker installation...'
                sh 'echo $PATH'
                sh 'which docker'
                sh 'docker --version'
                sh 'docker info'
            }
        }

        stage('Checkout') {
            steps {
                echo 'Checking out the code...'
                git url: 'https://github.com/FranciscoRamosMartins/devops-23-24-JPE-1231865.git'
            }
        }

        stage('Set Permissions') {
            steps {
                dir('CA2/Part2') {
                    echo 'Setting executable permissions on gradlew...'
                    sh 'chmod +x gradlew'
                }
            }
        }

        stage('Assemble') {
            steps {
                dir('CA2/Part2') {
                    echo 'Assembling the application...'
                    sh './gradlew assemble'
                }
            }
        }

        stage('Test') {
            steps {
                dir('CA2/Part2') {
                    echo 'Running unit tests...'
                    sh './gradlew test'
                }
            }
        }

        stage('Javadoc') {
            steps {
                dir('CA2/Part2') {
                    echo 'Generating Javadoc...'
                    sh './gradlew javadoc'
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'build/docs/javadoc',
                        reportFiles: 'index.html',
                        reportName: 'Javadoc'
                    ])
                }
            }
        }

        stage('Archive') {
            steps {
                dir('CA2/Part2') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }

        stage('Create Dockerfile') {
            steps {
                dir('CA2/Part2/') {
                    script {
                        def dockerfileContent = """
                        FROM tomcat:10
                        WORKDIR /app
                        COPY . .
                        EXPOSE 8080
                        ENTRYPOINT ["./gradlew"]
                        CMD ["bootRun"]
                        """
                        writeFile file: 'Dockerfile', text: dockerfileContent
                    }
                }
            }
        }

        stage('Publish Image') {
            steps {
                script {
                    echo 'Building and publishing Docker image...'
                    withEnv(["PATH=${env.PATH}"]) {
                        // Explicitly setting the Docker context to default
                        sh 'docker context use default'
                        docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                            dir('CA2/Part2') {
                                def customImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                                customImage.push('latest')
                            }
                        }
                    }
                }
            }
        }
    }
}
```

### Detailed Explanation

- **Pipeline Structure:** Similar to Part 1, the pipeline uses `agent any` to run on any available Jenkins agent.


- **Environment Variables:** Several environment variables are defined to facilitate Docker image creation and publication:

    - `DOCKER_CREDENTIALS_ID`: ID of the DockerHub credentials stored in Jenkins.
    - `DOCKER_IMAGE`: Name of the Docker image to be created.
    - `DOCKER_TAG`: Tag for the Docker image, set to the current build ID.
    - `PATH`: Ensures Jenkins uses the correct system paths.
  

- **Stages:**
    - **Check Docker:** This stage verifies Docker installation and configuration by checking the path, version, and system info. It uses several `sh` commands to ensure Docker is properly installed and accessible.
    - **Checkout:** Retrieves the code from the specified GitHub repository using the `git` step.
    - **Set Permissions:** Ensures the Gradle wrapper script (`gradlew`) is executable by setting the appropriate permissions.
    - **Assemble:** Compiles the application in the `CA2/Part2` directory using the `./gradlew assemble` command.
    - **Test:** Runs unit tests in the `CA2/Part2` directory using the `./gradlew test` command.
    - **Javadoc:** Generates Javadoc documentation using the `./gradlew javadoc` command and publishes it using the HTML Publisher plugin.
    - **Archive:** Archives the compiled JAR files using the `archiveArtifacts` step and applies fingerprinting for traceability.
    - **Create Dockerfile:** Dynamically creates a Dockerfile for the application. The Dockerfile specifies the base image (`tomcat:10`), sets the working directory, copies the application files, exposes port 8080, and defines the entrypoint and command to run the application.
    - **Publish Image:** Builds and publishes the Docker image to DockerHub. This stage uses several steps:
        - Sets the Docker context to default.
        - Logs in to DockerHub using the credentials stored in Jenkins.
        - Navigates to the `CA2/Part2` directory and builds the Docker image with the specified name and tag.
        - Pushes the Docker image to DockerHub with the current build ID and the `latest` tag.

## Troubleshooting

### Common Issues

1. **Jenkins not starting:**
    - Ensure you have the correct version of Java installed.
    - Check for any port conflicts.


2. **Plugin installation errors:**
    - Verify your network connection.
    - Check if the Jenkins update center is reachable.
    - Ensure all required plugins listed in the initial setup section are installed:
        - HTML Publisher
        - Docker Pipeline
        - Docker Commons Plugin
        - Docker Plugin
        - Docker API Plugin


3. **Permission issues:**
    - Ensure Jenkins has the necessary permissions to access your system directories and files.

### Docker-Related Issues on macOS

When installing Jenkins on a Mac using Homebrew, the second script might fail because Jenkins may not recognize the Docker installation. This issue can be resolved by uninstalling Jenkins from Homebrew and reinstalling it using the `jenkins.war` file:

1. **Uninstall Jenkins installed via Homebrew:**
    ```bash
    brew uninstall jenkins
    ```

2. **Install Jenkins using the war file:**
    ```bash
    java -jar jenkins.war --httpPort=[port number]
    ```

This approach ensures that Jenkins correctly identifies the Docker installation, allowing the second script to execute successfully.

### Tips

- Regularly update Jenkins and installed plugins to avoid security vulnerabilities and bugs.
- Use descriptive stage names and comments in your Jenkinsfile for better readability and maintenance.

## Conclusion

This guide provides detailed instructions for setting up Jenkins pipelines to automate your development workflow. The first pipeline covers essential stages like Checkout, Assemble, Test, and Archive. The second pipeline adds more sophisticated tasks, including generating Javadoc and publishing Docker images. 
Implementing these Jenkins pipelines will streamline your development process, making it more efficient and consistent. By automating build, test, and deployment tasks, you can focus on delivering high-quality software with minimal manual intervention.
