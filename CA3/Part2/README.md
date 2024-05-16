# Class Assignment 3: Virtualization and Vagrant

# Part 2: Vagrant

## Introduction
This document provides a comprehensive guide for Part 2 of Class Assignment 3 (CA3), focusing on using Vagrant to set up a virtual environment on macOS with Apple Silicon (M1/M2) chips. The goal of Part 2 is to execute a tutorial Spring Boot application (specifically the Gradle "basic" version developed in CA2, Part 2) within a virtual environment using Vagrant. This guide includes instructions for setting up the necessary virtual machines, configuring the Vagrantfile, and executing the Spring Boot application.

## Task 1: Setting Up the Vagrantfile

1. **Clone the Initial Solution Repository:**
    - Begin by cloning the initial solution repository from Bitbucket:
      ```bash
      git clone https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/
      ```

2. **Study the Vagrantfile in the macOS directory:**
    - Review the provided Vagrantfile within the cloned repository to understand its configuration and how it provisions two VMs:
      - **web:** VM for running Tomcat and the Spring Boot basic application.
        ```
        config.vm.define "web" do |web|
        web.vm.box = "perk/ubuntu-2204-arm64"
        web.vm.hostname = "web"

        web.vm.provider "qemu" do |qe|
        qe.arch = "aarch64"
        qe.machine = "virt,accel=hvf,highmem=off"
        qe.cpu = "cortex-a72"
        qe.net_device = "virtio-net-pci"
        qe.memory = "1G"
        qe.ssh_port = 50222
        qe.extra_qemu_args = %w(-netdev vmnet-host,id=vmnet,start-address=192.168.56.1,end-address=192.168.56.255,subnet-mask=255.255.255.0 -device virtio-net-pci,mac=52:54:00:12:34:51,netdev=vmnet)
        end

        web.vm.network "forwarded_port", guest: 8080, host: 8080

        web.vm.provision "file", source: "provision/netcfg-web.yaml", destination: "/home/vagrant/01-netcfg.yaml"
        web.vm.provision "shell", inline: <<-SHELL, privileged: false
        sudo mv /home/vagrant/01-netcfg.yaml /etc/netplan
        chmod 600 /etc/netplan/01-netcfg.yaml
        sudo netplan apply

        sudo apt install -y tomcat9 tomcat9-admin
        
        git clone https://bitbucket.org/pssmatos/tut-basic-gradle.git
        cd tut-basic-gradle
        chmod u+x gradlew
        ./gradlew clean build

        sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
        SHELL
        end
        ```
      - **db:** VM for executing the H2 server database.
        ```
        config.vm.define "db" do |db|
        db.vm.box = "perk/ubuntu-2204-arm64"
        db.vm.hostname = "db"

        db.vm.provider "qemu" do |qe|
        qe.arch = "aarch64"
        qe.machine = "virt,accel=hvf,highmem=off"
        qe.cpu = "cortex-a72"
        qe.net_device = "virtio-net-pci"
        qe.memory = "512"
        qe.ssh_port = 50122
        qe.extra_qemu_args = %w(-netdev vmnet-host,id=vmnet,start-address=192.168.56.1,end-address=192.168.56.255,subnet-mask=255.255.255.0 -device virtio-net-pci,mac=52:54:00:12:34:50,netdev=vmnet)
        end

        db.vm.network "forwarded_port", guest: 8082, host: 8082
        db.vm.network "forwarded_port", guest: 9092, host: 9092

        config.vm.provision "shell", inline: <<-SHELL
        wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
        SHELL

        db.vm.provision "file", source: "provision/netcfg-db.yaml", destination: "/home/vagrant/01-netcfg.yaml"
        db.vm.provision "shell", :run => 'always', inline: <<-SHELL
        sudo mv /home/vagrant/01-netcfg.yaml /etc/netplan
        chmod 600 /etc/netplan/01-netcfg.yaml
        sudo netplan apply

        java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
        SHELL
        end
        ```


3. **Copy the Vagrantfile and the provision directory:**
    - Copy the Vagrantfile and provision directory from the cloned repository (located inside the macOS directory) to CA3/Part2.


4. **Update the following code blocks in the Vagrantfile to install JDK 17 and include the path to clone your repository:**

    ```
    config.vm.provision "shell", inline: <<-SHELL
        sudo apt-get -y update
        sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
            openjdk-17-jdk-headless
    ```

    ```
    git clone https://github.com/FranciscoRamosMartins/devops-23-24-JPE-1231865.git
    cd devops-23-24-JPE-1231865/CA2/Part2
    ```
   

## Task 2: Installing Vagrant and QEMU on macOS with Apple Silicon

1. **Ensure Xcode Command Line Tools and Homebrew are Installed:**

    - Open Terminal and execute the following command to install Xcode Command Line Tools (if not already installed, you may need to first install Xcode from the App Store):
      ```
      sudo xcode-select --install
      ```

    - If Homebrew is not installed on your system, run the following command in Terminal to install it:
      ```
      /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
      ```

2. **Install QEMU and libvirt (Even if UTM is Installed):**

    - Install QEMU using Homebrew:
      ```
      brew install qemu
      ```

    - Next, install the `libvirt` library with Homebrew:
      ```
      brew install libvirt
      ```

3. **Install Vagrant and Plugins (Run the Following Commands in Terminal):**

    - Install Vagrant from the HashiCorp tap (preferred over the community version):
      ```
      brew install hashicorp/tap/hashicorp-vagrant
      ```

    - Install the Vagrant provider for QEMU:
      ```
      vagrant plugin install vagrant-qemu 
      ```

## Tas 3: Making your repository public

- When performing a `git clone` of a private repository, Git will prompt for authentication to read the password.


- This password prompt can disrupt the provision script, which ideally should run without requiring any user interaction.


- The easiest solution to prevent this issue is to make the repository public.


- To make your repository public on GitHub:
    - Navigate to your repository's settings.
    - Go to Repository Settings > General > Danger Zone.
    - Change the repository's visibility to public before executing your Vagrantfile.

## Task 4: Using Vagrant with QEMU Provider

- Execute the following command with `sudo` to bring up the Vagrant environment:
  ```
  sudo vagrant up
  ```

- Note: Since the machines were created with `sudo`, all subsequent Vagrant commands for these machines also require `sudo`, such as `sudo vagrant ssh`, `sudo vagrant destroy`, etc.


- Test the application by accessing the following URL in a web browser:
  ```
  http://localhost:8080/
  ```

### Troubleshooting Notes:

- If you encounter an error about "used ports" when running `vagrant up`, it may indicate that a machine did not shut down properly.
    - Check the status of running machines with:
      ```
      vagrant global-status
      ```
    - Destroy any lingering machines.
      ```
      sudo vagrant destroy
      ```
  
    - Additionally, if no machines are shown as running, check for any `qemu-system-aarch64` processes using:
      ```
      ps aux | grep qemu
      ```
    - If processes are found, terminate them by running:
      ```
      kill -9 PID
      ```
      Replace `PID` with the process ID (the number in the second column after `root`).

## Task 5: Finalizing and Tagging Repository

1. **Tagging the Repository:**
    - At the end of Part 2 of this assignment, tag your repository with `ca3-part2` to mark the completion of this phase.
   ```
    git tag ca3-part2
    git push origin ca3-part2
    ```
   
# Alternative Solution - VirtualBox

## Comparing QEMU and VirtualBox for Vagrant

## QEMU

**Advantages:**
- *Open Source:* QEMU is free and open-source software, which aligns well with the ethos of many Vagrant users who prefer open tools.
- *Support for Various Architectures:* QEMU supports emulating various CPU architectures, including ARM, which is compatible with Apple Silicon M1/M2/M3 processors.
- *Low-Level Control:* QEMU offers more low-level control over virtual machines, making it versatile for advanced users who need specific configurations or features.
- *Hypervisor Independence:* QEMU can run without a host hypervisor, allowing it to work well on systems where a hypervisor isn't available or desired.

**Disadvantages:**
- *Complexity:* QEMU can be more complex to set up and configure compared to VirtualBox, especially for beginners.
- *Performance:* Depending on the use case, QEMU might have slightly lower performance compared to other hypervisors like VirtualBox.

## VirtualBox

**Advantages:**
- *User-Friendly:* VirtualBox has a user-friendly interface and is relatively easy to set up and configure, making it more accessible for newcomers.
- *Documentation and Community Support:* VirtualBox benefits from extensive documentation and a large community, which can be helpful for troubleshooting issues.
- *Performance:* For many workloads, VirtualBox can offer good performance and efficiency.

**Disadvantages:**
- *Closed Source:* Unlike QEMU, VirtualBox is not fully open-source, which can be a consideration for users prioritizing open tools.
- *Host Dependency:* VirtualBox relies on a host hypervisor, which might be a limitation in certain environments.
- *Architecture Limitations:* VirtualBox primarily supports x86 architecture and does not officially support macOS hosts running on Apple Silicon (M1) processors.

## Using VirtualBox with Vagrant

- Instead of QEMU and libvirt, you will need to install VirtualBox.
- You will still need to install Vagrant and the Vagrant VirtualBox provider.
- The main difference in the Vagrantfile would be the database and webserver VM specific configurations, the most important being the box image and provider used.
    ```
  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end
    ```
  ```
  # Configurations specific to the database VM
  config.vm.define "db" do |db|
  db.vm.box = "ubuntu/focal64"
  db.vm.hostname = "db"
  db.vm.network "private_network", ip: "192.168.56.11"
    ```

## Conclusion

This guide has provided detailed instructions on using Vagrant to create a virtual environment on macOS with Apple Silicon for executing a Spring Boot application. Alternatively, the use of VirtualBox with Vagrant was discussed as an alternative to QEMU. Be aware, this alternative solution will not work on Apple Silicon (M1/M2/M3) chips due to VirtualBox's lack of official support for these processors. By following these steps, you will successfully configure and run the Spring application within the virtual machines provisioned using Vagrant. Ensure to document any modifications made to the Vagrantfile in the README for clarity and reproducibility. This concludes the setup and execution of CA3 Part 2 using virtualization with Vagrant.