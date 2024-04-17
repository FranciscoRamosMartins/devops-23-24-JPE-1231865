
# Class Assignment 2: Build Tools with Gradle

# Part 1: Enhancing a Java application using Gradle tasks
## Introduction
This document provides a comprehensive walkthrough of Part 1 of the Class Assignment 2 (CA2), detailing each step and command executed to fulfill the requirements. The Part1 of CA2 focuses on enhancing a Java application, implementing unit tests, and extending functionality through Gradle tasks. The assignment is structured into multiple tasks, each contributing to the overall improvement of the application. The following guide offers a detailed explanation of the steps taken to complete the assignment successfully.

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
    git commit -m "[Feat] - Close #9 Added a new task to be used to make a backup of the
                   sources of the application. Resulting backup directory added."
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
   git commit -m "[Feat] - Close #10 Added a new task of type Zip to be used to make an
                  archive (i.e., zip file) of the sources of the application."
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


#  Part 2: Converting a Java application from Maven to Gradle

## Introduction
This document provides a comprehensive walkthrough of Part 2 of the Class Assignment 2 (CA2), detailing each step and command executed to fulfill the requirements. Part 2 of CA2 focuses on converting a Java application from Maven to Gradle, updating the build scripts, adding a frontend plugin and some extra tasks, and ensuring the application builds and runs successfully using Gradle. The following guide offers a detailed explanation of the steps taken to complete the assignment successfully.

## Task 1: Starting a New Gradle Spring Boot Project, Copying the Maven Application, and Testing if it Runs

1. Begin by creating an Issue on GitHub titled "Create new branch "tut-basic-gradle", extract spring initializr zip to Part2, and add basic module from CA1. Test results with gradle commands." to track the changes made in this part of the assignment.


2. Create a new branch named "tut-basic-gradle" to work on this task:
   ```bash
   git checkout -b tut-basic-gradle
   ```

3. Create a folder for Part 2 of CA2:
   ```bash
   cd CA2
   mkdir Part2
   cd Part2
   ```

4. Go to https://start.spring.io and select the following options:


   - Project: Gradle - Groovy
   - Language: Java
   - Spring Boot: 3.2.4
   - Group: com.example
   - Artifact: demo
   - Name: demo
   - Package Name: com.example.demo
   - Packaging: Jar
   - Java: 17
   - Dependencies: Rest Repositories, Thymeleaf, Spring Data JPA, H2 Database


5. Generate and dowload the Spring Initializr zip file and copy the contents of the extracted folder to the Part2 folder:
    ```bash
    unzip react-and-spring-data-rest.zip
    cp -r react-and-spring-data-rest/* .
    rm -rf react-and-spring-data-rest
    ```

6. Copy the src folder from the basic module of CA1 to CA2/Part2:
    ```bash
    cp -r ../../CA1/basic/src .
    ```

7. Copy the webpack.config.js and package.json files from the basic module of CA1 to CA2/Part2:
    ```bash
    cp ../../CA1/basic/webpack.config.js .
    cp ../../CA1/basic/package.json .
    ```
   
8. Delete the src/main/resources/static/built/ folder, since this folder should be generated from the javascrit by the webpack tool:
    ```bash
    rm -rf src/main/resources/static/built/
    ```

9. Add these dependencies to the build.gradle file:
    ```groovy
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
        implementation 'org.springframework.boot:spring-boot-starter-data-rest'
        implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
        runtimeOnly 'com.h2database:h2'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
    }
    ```

10. In the Employee class, change all *javax.persistence* imports to *jakarta.persistence* in the Employee.java class:
    ```java
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.Id;
    ```
   
11. Build the project:
    ```bash
    ./gradlew build
    ```
    
12. Run the project:
    ```bash
    ./gradlew bootRun
    ```
    
13. Test the application by accessing the following URL in a web browser:
    ```
    http://localhost:8080/
    ```
    The page should be running, but blank, since the frontend is not yet configured.


14. Commit the changes and link them to the corresponding GitHub issue:
    ```bash
    git add .
    git commit -m "[Feat] - Close #12 Created new branch "tut-basic-gradle", extracted
                   spring initializr zip to Part2, and added basic module from CA1.
                   Tested results with gradle commands."
    git push
    ```

## Task 2: Adding a Frontend Plugin

1. Create an Issue on GitHub titled "Add the gradle plugin org.siouan.frontend to the project so that gradle is also able to manage the frontend."


2. Open the build.gradle file and add the frontend plugin:
   ```groovy
   id "org.siouan.frontend-jdk17" version "8.0.0"
   ```
   
3. Configure the plugin in the same build.gradle file:
   ```groovy
    frontend {
         nodeVersion = "16.20.2"
         assembleScript = "run build"
         cleanScript = "run clean"
         checkScript = "run check"
    }
    ```
   
4. Update the scripts section in the package.json file to configure the execution of webpack:
   ```groovy
   "scripts": {
       "webpack": "webpack",
       "build": "npm run webpack",
       "check": "echo Checking frontend",
       "clean": "echo Cleaning frontend",
       "lint": "echo Linting frontend",
       "test": "echo Testing frontend"
   },
   ```
   
5. Add the package manager to the package.json file, before the scripts section:
   ```groovy
    "packageManager": "npm@9.6.7",
    ```
   
6. Build the project:
   ```bash
   ./gradlew build
   ```
   
7. Run the project:
   ```bash
   ./gradlew bootRun
   ```

8. Test the application by accessing the following URL in a web browser:
   ```
   http://localhost:8080/
   ```
   If the page is no longer blank, the frontend is working correctly.


9. Commit the changes and link them to the corresponding GitHub issue:
   ```bash
   git add .
   git commit -m "[Feat] - Close #13 Added the gradle plugin org.siouan.frontend to the
                  project so that gradle is also able to manage the frontend."
   git push
   ```

## Task 3: Adding a Task to Copy a Generated jar to a Folder Named ”dist” Located at the Project Root Folder Level

1. Create an Issue on GitHub titled "Add a task to gradle to copy the generated jar to a folder named ”dist” located at the project root folder level."


2. Open the build.gradle file and add the copyJar task:
   ```groovy
   task copyJar(type: Copy) {
       dependsOn build
   
       from "$buildDir/libs"
       into "$projectDir/dist"

       include "*.jar"
   }
   ```

3. Build the project:
   ```bash
   ./gradlew build
   ```
   
4. Run the copyJar task to copy the generated jar to the dist folder:
   ```bash
   ./gradlew copyJar
   ```
   
5. Ensure the "dist" folder exists and contains the generated jar file.


6. Commit the changes and link them to the corresponding GitHub issue:
   ```bash
   git add .
   git commit -m "[Feat] - Close #14 Added a task to gradle to copy the generated jar
                  to a folder named ”dist” located at the project root folder level."
   git push
   ```

## Task 4: Adding a Task to Delete the Files Generated by Webpack Executed Automatically by Gradle Before the Task Clean

1. Create an Issue on GitHub titled "Add a task to gradle to delete all the files generated by webpack (usually located at src/resources/main/static/built/). This new task should be executed automatically by gradle before the task clean."


2. Open the build.gradle file and add a cleanBuiltFiles task:
   ```groovy
   task cleanBuiltFiles(type: Delete) {
       delete fileTree(dir: 'src/main/resources/static/built', include: '**/*')
   }
   ```
   
3. Add the following line to make sure this task is executed automatically by the task clean:
   ```groovy
   clean.dependsOn cleanBuiltFiles
   ```
   
4. Build the project:
   ```bash
    ./gradlew build
    ```
   
5. Run the clean task to ensure the cleanBuiltFiles task is executed:
    ```bash
     ./gradlew clean
     ```
   
6. Verify that the files generated by webpack have been deleted.


7. Commit the changes and link them to the corresponding GitHub issue:
   ```bash
   git add .
   git commit -m "[Feat] - Close #15 Added a task to gradle to delete all the files
                  generated by webpack located at src/resources/main/static/built/.
                  This new task is executed automatically by gradle before the task
                  clean."
   git push
   ```
   
## Task 5: Merging to the Main Branch and Tagging the Repository

1. Merge the changes from the "tut-basic-gradle" branch to the main branch:
   ```bash
   git checkout main
   git merge --no-ff tut-basic-gradle
   ```
   
2. Tag the repository with "ca2-part2":
   ```bash
    git tag ca2-part2
    git push origin ca2-part2
    ```

## Alternative solution - Maven
Another approach to the task could involve opting for an alternative build automation tool. Instead of employing Gradle, Maven could be utilized. Maven, predominantly employed for Java projects, offers a similar functionality to Gradle but relies on an XML file to delineate the project's configuration and dependencies. The process for implementing these adjustments using Maven would entail:
1. Create an issue on GitHub titled "Implement Maven alternative."


2. Copy src, mvnw, mvnw.cmd, webpack.config.js, package.json, pom.xml, and the contents of .mvn (except maven-wrapper.jar) from the basic module of CA1 to CA2/Part2_Maven.


3. In this case, there is no need to add the frontend plugin, it should already be present.


4. Add the following plugin to copy the generated jar to a folder named "dist":
    ```xml
    <plugin>
         <artifactId>maven-resources-plugin</artifactId>
         <version>3.2.0</version>
         <executions>
              <execution>
                <id>copy-resources</id>
                <phase>package</phase>
                <goals>
                     <goal>copy-resources</goal>
                </goals>
                <configuration>
                     <outputDirectory>${project.basedir}/dist</outputDirectory>
                     <resources>
                          <resource>
                            <directory>${project.build.directory}</directory>
                            <includes>
                                 <include>*.jar</include>
                            </includes>
                          </resource>
                     </resources>
                </configuration>
              </execution>
         </executions>
    </plugin>
    ```
5. Add the following plugin to delete the files generated by webpack during the clean phase:
   ```xml
    <plugin>
         <artifactId>maven-clean-plugin</artifactId>
         <version>3.1.0</version>
         <executions>
              <execution>
                <id>clean-webpack</id>
                <phase>pre-clean</phase>
                <goals>
                     <goal>clean</goal>
                </goals>
                <configuration>
                     <filesets>
                          <fileset>
                            <directory>src/main/resources/static/built</directory>
                          </fileset>
                     </filesets>
                </configuration>
              </execution>
         </executions>
    </plugin>
   ```
6. Build the project:
    ```bash
    ./mvnw install
    ```
    
7. Run the project:
    ```bash
    ./mvnw spring-boot:run
    ```
    
8. Test the application by accessing the following URL in a web browser:
    ```
    http://localhost:8080/
    ```
   If the page looks the same as in the gradle project, the frontend should be working correctly.


9. Test if the jar file is copied to the "dist" folder (in this alternative solution, this is done automatically when the project is built):
    ```bash
    ls dist
    ```
   If the jar file is present, the task was executed successfully.


10. Test if the files generated by webpack are deleted before the clean task is executed:
      ```bash
      ./mvnw clean
    ls src/main/resources/static/built
      ```
If the files are deleted, the task was executed successfully.

11. Commit the changes and link them to the corresponding GitHub issue:
    ```bash
    git add .
    git commit -m "[Feat] - Close #16 Implemented Maven alternative."
    git push
    ```

## Conclusion

This walkthrough has provided a detailed guide on how to convert a Java application from Maven to Gradle. By following the steps outlined in this document, the Class Assignment 2 (CA2) should have been successfully completed. The assignment involved starting a new Gradle Spring Boot project, adding a frontend plugin, and implementing additional tasks to copy a generated jar to a specific folder and delete the files generated by webpack. By merging the changes to the main branch and tagging the repository with "ca2-part2," the completion of Part 2 of the assignment has been marked. The alternative solution using Maven provides an insight into how the same tasks could be accomplished using a different build automation tool.