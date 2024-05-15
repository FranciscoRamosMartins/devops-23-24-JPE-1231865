# Class Assignment 3: Virtualization and Vagrant

# Part 1: Using Maven and Gradle in an Ubuntu Virtual Machine on Apple Silicon

## Introduction
This document provides a comprehensive walkthrough of Part 1 of Class Assignment 3 (CA3), detailing each step and command executed to set up a UTM virtual machine (VM) with Ubuntu for Apple Silicon (ARM64). The goal of Part 1 is to practice using UTM to run projects from previous assignments inside the VM. This guide includes instructions for creating and configuring the VM, cloning the individual repository, installing necessary dependencies, and addressing potential issues encountered during setup.

## Task 1: Setting Up UTM with an Ubuntu VM

1. **Download Ubuntu:**
    - Download the ARM version of Ubuntu Server LTS from [Ubuntu's official website](https://ubuntu.com/download/server/arm).
    - Save the downloaded .iso file for later use during VM setup.


2. **Install and Configure UTM:**
    - Download UTM from [mac.getutm.app](https://mac.getutm.app/).
    - Install UTM by opening the .dmg file and moving UTM to the Applications directory.
    - Launch UTM and proceed to set up a new virtual machine.


3. **Create Ubuntu VM:**
    - Open UTM and click the '+' button to add a new VM.
    - Choose virtualization (for optimal performance) and select Linux as the operating system.
    - Locate the downloaded Ubuntu .iso file as the boot image for the VM.
    - Customize hardware settings (RAM, virtual hard disk size) according to Ubuntu's minimum requirements.


4. **Boot and Install Ubuntu:**
   - Start the VM and follow on-screen prompts to begin the Ubuntu installation.
   - Select "Try" or "Install Ubuntu" from the GRUB menu.
   - Choose your preferred system language to initiate the installation process.
   - Select your keyboard layout and variant.
   - Choose "Ubuntu Server" as the type of system installation.
   - Accept the default network interface configuration.
   - Leave the proxy configuration as is. The setup will determine an appropriate proxy server for package download.
   - On the guided storage configuration screen, select "Use an entire disk."
   - Confirm the disk to be erased (virtual disk, not your host laptop's physical disk) and choose "Continue" to proceed.
   - Enter your desired username, password, and hostname.
   - Skip the upgrade to Ubuntu Pro for now.
   - Skip the installation of the OpenSSH server and featured server snaps.
   - Wait for the installation process to complete. Once finished, restart the virtual machine when prompted.
   - After restarting, log in with the username and password you configured during setup.
   - You can now enter and execute commands on the Linux terminal.
   - At the end of the installation process, do not click “Reboot Now” yet.
   - With the VM selected in the left-hand sidebar, click the CD/DVD dropdown menu and then click "clear". This will remove the Ubuntu installation .iso so that the VM boots with the new installation, rather than booQng the installer again.

## Task 2: Cloning Your Repository

1. **Install Git in your Ubuntu VM:**
   ```bash
   sudo apt install git
   ```


2. **Generate an SSH key pair on the Ubuntu VM:**
   ```bash
   ssh-keygen -t rsa
   ```

3. **Display the public key using the following command:**
   ```bash
   cat ~/.ssh/id_rsa.pub
   ```
   
4. **Copy the public key and add it to your GitHub account:**

- Under Settings > SSH and GPG keys > New SSH key.
   
- Note: If, for some reason, you cannot use copy/paste in your VM, you can use the following command to send the key to your host machine and then copy it from there:
   ```bash
   scp ~/.ssh/id_rsa.pub franciscomartins@198.1622.1.210:/Users/franciscomartins/desktop
   ```


3. **Inside the Ubuntu VM, clone your individual repository using the SSH URL:**
   ```bash
   git clone git@github.com:FranciscoRamosMartins/devops-23-24-JPE-1231865.git
   ```



## Task 3: Installing Project Dependencies

1. **Install JDK 21 (Java Development Kit):** 
   ```bash
   sudo apt update
   sudo apt install openjdk-21-jdk
   ```
   Note: Some projects will not work with previous versions of Java (e.g., Java 17). Ensure you have the correct version installed.


2. **Install Maven:**
   ```bash
   sudo apt update
   sudo apt install maven
   ```


3. **Install Gradle:**
   ```bash
   sudo apt update
   sudo apt install gradle
   ```


## Task 4: Building and Running the Project from CA1 and Accessing the Web Application

1. **Use the following commands to build and run the project:**
   ```bash
   cd devops-23-24-JPE-1231865/CA1/basic
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
   
2. **Access the Web Application:**
   - Determine the IP address of your VM with the `ip a` command.
   - Open a web browser on your host machine.
   - Enter the IP address of the Ubuntu VM followed by the port number specified in the application: `192.168.64.2:8080`

## Task 5: Building the Project from CA2/Part1, Running the Server in the VM and the Client in the Host Machine

1. **Build the project and run the server in the VM:**
   ```bash
   cd devops-23-24-JPE-1231865/CA2/Part1
   ./gradlew build
   ./gradlew runServer
   ```
   
2. **Build the project and run the client in the host machine:**
   ```bash
   cd devops-23-24-JPE-1231865/CA2/Part1
   ./gradlew build
   ./gradlew runClient --args="192.168.64.2 8080"
   ```
   This time, you need `--args="192.168.64.2 8080"` to specify the IP address of the VM and the port number specified in the application.

## Task 6: Building and Running the Project from CA2, Part 2

1. **Use the following commands to build and run the project:**
   ```bash
   cd devops-23-24-JPE-1231865/CA2/Part2
   ./gradlew build
   ./gradlew bootRun
   ```
   
2. **Access the Web Application:**
    - Open a web browser on your host machine.
    - Enter the IP address of the Ubuntu VM followed by the port number specified in the application: `192.168.64.2:8080`

## Conclusion

This walkthrough has provided a detailed guide on setting up a UTM VM with Ubuntu for Apple Silicon and running projects from previous assignments inside the VM environment. By following the outlined steps, the UTM setup for Class Assignment 3 (CA3) Part 1 should be completed successfully. Attention to detail, including installing necessary dependencies and addressing potential issues, ensures a smooth execution of the Spring Boot and Gradle projects within the Ubuntu VM environment.