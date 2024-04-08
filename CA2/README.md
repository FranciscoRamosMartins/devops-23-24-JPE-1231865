
# Class Assignment 2 Walkthrough: Enhancing a Java application using Gradle tasks

## Introduction
This document provides a comprehensive walkthrough of the Class Assignment 2 (CA2), detailing each step and command executed to fulfill the requirements. The CA2 focuses on enhancing a Java application, implementing unit tests, and extending functionality through Gradle tasks. The assignment is structured into multiple tasks, each contributing to the overall improvement of the application. The following guide offers a detailed explanation of the steps taken to complete the assignment successfully.

## Task 1: Downloading the Example Application

1. Begin by creating an Issue on GitHub titled "Download and commit to repository (in a folder for Part 1 of CA2) the example application." to track the changes made in this part of the assignment.


2. Create a folder for CA2 with a subfolder for Part 1:
   ```bash
   mkdir CA2
   cd CA2
   mkdir Part1
   ```

3. Clone the gradle demo repository in folder CA2/Part1:
   ```bash
   cd Part1
   git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git
   ```

4. Remove the .git directory from the cloned repository:
   ```bash
   cd gradle_basic_demo
   rm -rf gradle_basic_demo/.git
   ```

5. Move all files inside the "gradle_basic_demo" directory to CA2/Part1, and remove the empty directory:
   ```bash
   mv gradle_basic_demo/* .
   rm -rf gradle_basic_demo
   ```

6. Add all the new files to the GitHub repository:
   ```bash
   git add .
   git commit -m "[Feat] - Close #6 Cloned gradle demo repository in folder CA2/Part1."
   git push
   ```

## Task 2: Building and Running the Application
1. Build the Gradle project:
   ```bash
   ./gradlew build
   ```

2. Start the server on port 8080:
   ```bash
   java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 8080
   ```

3. Start the client using the Gradle task runClient and verify it is connecting to the server:
   ```bash
   ./gradlew runClient
   ```
   

## Task 3: Adding a Task to Run the Server with Gradle

1. Create an Issue on GitHub titled "Add a new task to execute the server."


2. Open the build.gradle file and add the following task:
   ```groovy
   task runServer(type: JavaExec, dependsOn: classes) {
       group = "DevOps"
       description = "Launches a chat server that listens on port 8080"

       classpath = sourceSets.main.runtimeClasspath

       mainClass = 'basic_demo.ChatServerApp'

       args '8080'
   }
   ```

3. Refresh the Gradle project to start using the newly added task:
   ```bash
   ./gradlew build
   ```

4. Run the server using the new task:
   ```bash
   ./gradlew runServer
   ```

5. Start the client after the server is running to check if it connects successfully:
   ```bash
   ./gradlew runClient
   ```
   
6. Commit the changes and link them to the corresponding GitHub issue:
   ```bash
   git add .
   git commit -m "[Feat] - Close #7 Added a new task to execute the server."
   git push
   ```


## Task 4: Adding a Unit Test and Updating the Gradle Script

1. Create an Issue on GitHub titled "Add a simple unit test and update the gradle script so that it is able to execute the test."


2. Add the JUnit 4.12 dependency to the build.gradle file:
   ```groovy
   dependencies {
       testImplementation 'junit:junit:4.12'
   }
   ```

3. Refresh the Gradle project to download the new dependency:
   ```bash
   ./gradlew build
   ```

4. Create a test class named AppTest with a unit test for the App class.
   ```java
   package basic_demo;

   import org.junit.Test;

   import static org.junit.Assert.*;

   public class AppTest {
       @Test
       public void testAppHasAGreeting() {
           App classUnderTest = new App();
           assertNotNull("app should have a greeting", classUnderTest.getGreeting());
       }
   }
   ```

5. Run the test to ensure it passes:
   ```bash
   ./gradlew test
   ```

6. Commit the changes and link them to the corresponding GitHub issue:
   ```bash
   git add .
   git commit -m "[Test] - Close #8 Added a unit test and updated the gradle script
                           so that it is able to execute the test."
   git push
   ```


## Task 5: Adding a Task to Copy Source Contents to a Backup Directory

1. Create an Issue on GitHub titled "Add a new task of type Copy to be used to make a backup of the sources of the application."


2. Add the following task to the build.gradle file:
   ```groovy
   task copySource(type: Copy) {
       group = "DevOps"
       description = "Copies source files to a backup directory"

       from 'src/'
       into 'backup'
   }
   ```

3. Reload Gradle to apply the changes:
   ```bash
   ./gradlew build
   ```

4. Run the task and make sure it copies the source files to the backup directory:
   ```bash
   ./gradlew copySource
   ```

5. Commit the changes and link them to the corresponding GitHub issue:
   ```bash
    git add .
    git commit -m "[Feat] - Close #9 Added a new task to be used to make a backup of the sources
                            of the application. Resulting backup directory added."
    git push
    ```


## Task 6: Adding a Task to Archive Source Content

1. Create an Issue on GitHub titled "Add a new task of type Zip to be used to make an archive (i.e., zip file) of the sources of the application."


2. Add the following task to the build.gradle file:
   ```groovy
   task zip(type: Zip){
      group = "DevOps"
      description = "Zips the source files"

      from 'src/'
      archiveFileName = 'source.zip'
      getArchiveFile().set(layout.buildDirectory.file('backup/source.zip'))
   }
   ```

3. Reload Gradle to apply the changes:
   ```bash
   ./gradlew build
   ```

4. Run the task to create the zip archive:
   ```bash
   ./gradlew zip
   ```

5. Commit the changes and link them to the corresponding GitHub issue:
   ```bash
   git add .
   git commit -m "[Feat] - Close #10 Added a new task of type Zip to be used to make an archive
                           (i.e., zip file) of the sources of the application."
   git push
   ```


## Task 7: Tagging the Repository

1. Mark the repository with the tag "ca2-part1":
   ```bash
   git tag ca2-part1
   git push origin ca2-part1
   ```
   
## Conclusion

This walkthrough has provided a detailed guide on how to enhance a Java application using Gradle tasks. By following the steps outlined in this document, the Class Assignment 2 (CA2) should have been successfully completed. The assignment involved building and running the application, adding a task to execute the server, implementing a unit test, and creating additional tasks to copy and archive source files. By tagging the repository with "ca2-part1," the completion of Part 1 of the assignment has been marked.